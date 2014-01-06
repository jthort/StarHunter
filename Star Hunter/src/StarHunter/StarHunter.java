package StarHunter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class StarHunter extends JavaPlugin implements Listener{
	public final Logger logger = Logger.getLogger("Minecraft");


	StarHunter plugin;
	
	
	static List<String> list = new ArrayList<String>();
	
	@Override
	public void onEnable(){

		list.add("GOLD_BLOCK");
		list.add("IRON_BLOCK");
		list.add("BOOK");
		
		logger.info("StarHunter has been enabled");
		PluginManager pm = getServer().getPluginManager();
		final FileConfiguration config = this.getConfig();
		
		  pm.registerEvents(this, this);
		pm.registerEvents(new fallingblocks(this), this);
		
		config.addDefault("Star Size", 4);
		config.addDefault("Cooldown Timer", 72000);
		config.addDefault("TP Delay", 100);
		config.addDefault("Mining Time", 1200);
		config.addDefault("Max X", 1000);
		config.addDefault("Max Z", 1000);
		config.addDefault("Min X", 100);
		config.addDefault("Min Z", 100);
		config.addDefault("Random Drops", true);
		config.addDefault("If Random is false, list the items you want dropped from the star", list);
		
		config.options().copyDefaults(true);
		saveConfig();
	}
   @Override
   public void onDisable(){
	   try { fallingblocks.removal(); } catch (NullPointerException ex) {
		
	   }
	   this.logger.info("StarHunter has been disabled and removed the current starhunt!");
		
	}

 
int timer = 0;
int countdown = 0;
int cdmessages = 0;
int messages = 0;
static int multi = 0;
public static int maxX;
public static int minX;
public static int minZ;
public static int maxZ;
public static int where;
public static int lo;
public static int a;
public static Material c;
public static Boolean randoms;
public static int tpblocker = 0;
int test;
public static int sup = 0;
static List<Material> ah = new ArrayList<Material>();
public static String b;
	boolean wait = true;

	
	@Override
	public boolean onCommand(final CommandSender sender, Command command, final String speak, final String[] args) {
		
	lo = 0;
	
		
		if(speak.equalsIgnoreCase("starhunt") || (speak.equalsIgnoreCase("sh"))){
		
			
		switch (args.length){
		
		case 0:
		
			sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "Invalid StarHunt command type " + ChatColor.RED + "/starhunt help" + ChatColor.YELLOW + " for the commands");
			break;
			
		case 1:
			
			if(args[0].equalsIgnoreCase("reload")){
			   if(sender.hasPermission("sh.reload")){
					reloadConfig();
					sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "Config reloaded");
			   }else{
				   sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "You don't have the permission to perform this command!");
			   }
			}
	
			if(args[0].equalsIgnoreCase("where")){
			
					 if(fallingblocks.loc == null || multi == 0){
					sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "There is no starhunt running right now");
				}else if(where == 1){
					sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "The star hunt is still counting down");
				}else{
					
					double x = fallingblocks.loc.getX();
					double y = fallingblocks.loc.getY();
					double z = fallingblocks.loc.getZ();
					String wrld = fallingblocks.loc.getWorld().getName();
				sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "The starhunt is currently at " + ChatColor.GREEN + "X " + x + " Y " + y + " Z " + z + ChatColor.YELLOW + " at world " + ChatColor.GREEN + wrld);
				}
				 
			}
			
			if(args[0].equalsIgnoreCase("help")){
				if (sender.hasPermission("sh.help")){
				sender.sendMessage(ChatColor.GRAY + "--------------" + ChatColor.GOLD + "[StarHunter]" + ChatColor.GRAY + "-------------");
				sender.sendMessage(ChatColor.RED + "/starhunt start <world> " + ChatColor.GRAY + "(Starts the starhunt)");
				sender.sendMessage(ChatColor.RED + "/starhunt stop  " + ChatColor.GRAY + "(Stops StarHunter)");
				sender.sendMessage(ChatColor.RED + "/starhunt start now <world> " + ChatColor.GRAY + "(Starts the StarHunt without a countdown)");
				sender.sendMessage(ChatColor.RED + "/starhunt info  " + ChatColor.GRAY + "(Plugin information)");
				sender.sendMessage(ChatColor.RED + "/starhunt tp  " + ChatColor.GRAY + "(teleports to the star location");
				sender.sendMessage(ChatColor.RED + "/starhunt where  " + ChatColor.GRAY + "(Gives the cords to the current starhunt)");
				sender.sendMessage(ChatColor.RED + "/starhunt reload  " + ChatColor.GRAY + "(Reloads the configuration file)");
				}else{
					sender.sendMessage(ChatColor.GRAY + "--------------" + ChatColor.GOLD + "[StarHunter]" + ChatColor.GRAY + "-------------");
					sender.sendMessage(ChatColor.RED + "/starhunt where  " + ChatColor.GRAY + "(Gives the cords to the current starhunt)");
					sender.sendMessage(ChatColor.RED + "/starhunt info  " + ChatColor.GRAY + "(Plugin information)");
				}
			}
			
			if(args[0].equalsIgnoreCase("stop")){
			
				  if (sender.hasPermission("sh.stop")){
					  
					  Bukkit.getScheduler().cancelTask(timer);
					
					  multi = 0;
					  test = 0;
					  messages = 0;
					  where = 0;
					  fallingblocks.counter = 0;
					  
			
				fallingblocks.counter = 0;
					
					  try { fallingblocks.removal(); } catch (NullPointerException ex) {
						   
						   }
					  Bukkit.broadcastMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "The hunt has stopped!");
				}else{
					sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "You don't have the permission to perform this command!");
				}
				}
			
			
			
			if(args[0].equalsIgnoreCase("info") || (args[0].equalsIgnoreCase("information"))){
				sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.GRAY + "Plugin Version - " + ChatColor.RED + "2.0");
			}
			
			
			
			if(args[0].equalsIgnoreCase("tp")){
				if (sender.hasPermission("sh.tp")){
					if(tpblocker == 1){
						sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "You're already teleporting, don't spam!");
						
					}else{
					Location loc = fallingblocks.loc;
					if(loc != null && multi != 0){
					    cdmessages++;
					    
					    
					    if(messages != 0){
					    	sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "The Starhunt is counting down now, wait for it to start");
						}else{
							 
							
					    
					    
						 countdown =  Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
								public void run(){
									tpblocker = 1;
								    switch(cdmessages){
								    case 1:
								    	int delay = getConfig().getInt("TP Delay");
										int delaydone = delay/20;
										sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "Teleporting in " + delaydone + " seconds, please wait...");
								    	cdmessages++;
								    	break;
								    case 2:
								    	
								    	World q = Bukkit.getWorld(b);
								    	Location loc = fallingblocks.loc;
										double f = loc.getY();
										double t = q.getHighestBlockYAt(loc);
										 loc.setY(f);
										loc.setY(t);
										if(sender instanceof Player){
											Player player = (Player) sender;
											player.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "Teleporting..");
											player.teleport(loc);
											tpblocker = 0;
											
										}else{
											sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "You cannot teleport in the console");
										}
										
										
										
										Bukkit.getScheduler().cancelTask(countdown);
										cdmessages = 0;
								    	break;
								    
								    }
							    	
							    	
							    	
							 
							    }
							    
							}, 0, getConfig().getInt("TP Delay"));
						
	
						}
		
				
				
				}else{
					sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "There is no star!");
				}
					}
				}else{
					sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "You don't have the permission to perform this command!");
				}
			}
			
			if(args[0].equalsIgnoreCase("start")){
				sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "Incorrect Usage: /sh start <world>");
				return true;
			}
				
				
		
		case 2:
			
			if(args[0].equalsIgnoreCase("start")){
				if (sender.hasPermission("sh.start")){
					final String f = args[1];
					if (multi == 0){
					
		//countdown
				   b = f;
				   
				   World d = Bukkit.getWorld(b);
				   if(args[1].equalsIgnoreCase("now")){
					   sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "Incorrect Usage: /sh start now <world>");
				   }else if(d == null){
					   sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "Incorrect Usage: /sh start <world>");
				   }else{
					   multi++;
						sup = 0;
					   messages = 0;
				 where = 1;
				   
				 timer =  Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
					public void run(){
						int msg1 = (getConfig().getInt("Cooldown Timer"))/20;
						int msg2 = (getConfig().getInt("Cooldown Timer")* 2/3)/20;
						int msg3 = (getConfig().getInt("Cooldown Timer")* 1/3)/20;
						String time;
				    	switch(messages){
				    	
				    	
				    		case 0:
				    			if(msg1 < 60){
				    				time = "Seconds";
				    			}else{
				    				msg1 = msg1/60;
				    				time = "Minutes";
				    			}
				    			Bukkit.broadcastMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "The Star Hunt starts in " + msg1 + " " + time);
				    			messages++;
				    			break;
				    		case 1:
				    			if(msg2 < 60){
				    				time = "Seconds";
				    				
				    			}else{
				    				msg2 = msg2/60;
				    				time = "Minutes";
				    			}
				    			Bukkit.broadcastMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "The Star Hunt starts in " + msg2 + " " + time);
				    			messages++;
				    			break;
				    		case 2:
				    			if(msg3 < 60){
				    				time = "Seconds";
				    			}else{
				    				msg3 = msg3/60;
				    				time = "Minutes";
				    			}
				    			Bukkit.broadcastMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "The Star Hunt starts in " + msg3  + " " + time);
				    			messages++;
				    			break;
				    		case 3:

				    			a = getConfig().getInt("Star Size");
				    			
				    			c = Material.getMaterial(getConfig().getString("Star Material"));
				    			
				    			maxX = getConfig().getInt("Max X");
				    			maxZ = getConfig().getInt("Max Z");
				    			minX = getConfig().getInt("Min X");
				    			minZ = getConfig().getInt("Min Z");
				    			list = getConfig().getStringList("If Random is false, list the items you want dropped from the star");
				    			randoms = getConfig().getBoolean("Random Drops");
				    			Bukkit.getScheduler().cancelTask(timer);
				    
				    		messages = 0;
				    		 if(maxX <= minX || minZ >= maxZ){
			    				 sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "Configuration error, the max distance must be greater than the min distance");
			    			 }else{
				    			where = 0;
				    			fallingblocks.loc();
			    			 }
				    			break;
				    	
				    	}
					
				    }
				    
				}, 0, getConfig().getInt("Cooldown Timer")/3);
				   }
				   
							}else{
								sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "The starhunt is already running!");
									}
					
				
			break;
				}else{
					sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "You don't have the permission to perform this command!");
				}
		
			}

			break;
	
		case 3:
			if(args[0].equalsIgnoreCase("start")){
				if(args[1].equalsIgnoreCase("now")){
					if (sender.hasPermission("sh.startnow")){
						 
						String m = args[2];
						World o = Bukkit.getWorld(m);
						
						if(o != null){
							
						
						if(multi == 0){
							
						
					Bukkit.getScheduler().cancelTask(timer);
					b = args[2];
		    			a = getConfig().getInt("Star Size");
		    			c = Material.getMaterial(getConfig().getString("Star Material"));
		    			maxX = getConfig().getInt("Max X");
		    			maxZ = getConfig().getInt("Max Z");
		    			minX = getConfig().getInt("Min X");
		    			minZ = getConfig().getInt("Min Z");
		    			list = getConfig().getStringList("If Random is false, list the items you want dropped from the star");
		    		
		    			randoms = getConfig().getBoolean("Random Drops");
		    			 if(maxX <= minX || minZ >= maxZ){
		    				 sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "Configuration error, the max distance must be greater than the min distance");
		    			 }else{
		    				 
		    			 
		    			
						fallingblocks.loc();
						multi++;
		    			 }
						}else{
							sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "You are already running a starhunt");
						}
				}else{
					sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "Not a valid world: /sh start now <World>");
				}
					}else{
						sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "You don't have the permission to perform this command!");
					}
				}
			}
			
			break;
			
		default:
			
			sender.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + "Invalid StarHunt command, type /starhunt for the commands");
			
	        break;
	   
			}
		}
		
		return true;
    }
	
	
	
	
	}

