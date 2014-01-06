package StarHunter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;


public class fallingblocks extends StarHunter{


	static StarHunter plugin;
	
	
	int x = 1;
	static int s = a;
	public static Location j;
	static Location loc;
	public static int counter = 0;

	 static List<ItemStack> p = new ArrayList<ItemStack>();
	 static List<Location> blocks = new ArrayList<Location>();
	 static List<Block> bl = new ArrayList<Block>();
	 public static HashMap<Location, Material> map = new HashMap<Location, Material>();
	 
	public fallingblocks(StarHunter instance) {
		plugin = instance;
	
		
	}
	
	
	
	public static void removal(){
	
		for(Location l : sphere(j, a)){
			
			Material b = map.get(l);
			l.getBlock().setType(b);
		}
	
	}
	
	
	
	
	public static Location loc(){
		map.clear();
		Random xr = new Random();

		Random zr = new Random();
		 int x = xr.nextInt(maxX);
		 int z = zr.nextInt(maxZ);
		
		
				
			
				
			
		 
		 do {
			Random g = new Random();
			Random gs = new Random();
			
			x = g.nextInt(maxX);
			z = gs.nextInt(maxZ);
			
				
		} while ( x < minX || z < minZ);
		
			 
		 
		 
		 
		 World worldx = Bukkit.getWorld(StarHunter.b);
		int y = worldx.getHighestBlockYAt(x, z);
		
		
		loc = new Location(worldx, x, y, z);
		
	    j = new Location(worldx, x, y, z);
		
		for(Location l : sphere(loc, a)){
			map.put(l, l.getBlock().getType());
			
		    l.getBlock().setType(c);
		    blocks.add(l.getBlock().getLocation());
		  
		}
		
		
		loc.getBlock().setType(Material.DIAMOND_BLOCK);
		
		
		String m = worldx.getName();
		
		Bukkit.broadcastMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "The starhunt is starting at location: " + ChatColor.RED + "x " + x + " y " + y + " z " + z + ChatColor.YELLOW + " In world "+ ChatColor.RED + m);
		//q.spawnFallingBlock(loctest16, Material.GOLD_ORE, (byte) 0); 
		 worldx.loadChunk(loc.getChunk());
			
			
		return loc;
			
	}
	
	
	//Copy and pasted from a bukkit thread, this is not mine
	public static List<Location> sphere(Location cent, double r) {
        List<Location> sphereblocks = new ArrayList<Location>();
        double cx = cent.getX();
        double cy = cent.getY();
        double cz = cent.getZ();
        for (double x = cx - r; x <= cx + r; x++) {
            for (double y = cy - r; y <= cy + r; y++) {
                for (double z = cz - r; z <= cz + r; z++) {
                    Location l = new Location(cent.getWorld(), x, y, z);
                    double dist = cent.distance(l);
 
                    if (dist <= r) {
                        sphereblocks.add(l);
                    }
                }
            }
        }
        return sphereblocks;
      
    }
		
	
	  @EventHandler(priority = EventPriority.HIGH)
	  public void onBlockBreak(BlockBreakEvent event){

		  Player p = event.getPlayer();
	Block a = event.getBlock();
	 Location world = a.getLocation();

	
	 
		if(fallingblocks.blocks.contains(a.getLocation())){
		
			 if (a.getType() == Material.DIAMOND_BLOCK){
				
				world.getBlock().setType(Material.AIR);
				
				for(int e = 0; e < 4; e++){
				Random rand = new Random();
				 Material[] materiallists = Material.values();
				 
				 
				 int size = materiallists.length;
				 int index = rand.nextInt(size);
				 
				 
				 Material randomMaterial = materiallists[index];
				 ItemStack item = new ItemStack(randomMaterial);
				 Material items = item.getType();
				 
				 p.getWorld().dropItemNaturally(world.getBlock().getLocation(), new ItemStack(items));
				
				}
				
				int temp = (getConfig().getInt("Mining Time"))/20;
				String tim;
				if(temp > 59){
					temp = temp/60;
					tim = "minutes";
				}else{
					tim = "seconds";
				}
				String user = p.getName();
				
				p.sendMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.YELLOW + "You have " + ChatColor.RED + temp + " " + tim + ChatColor.YELLOW + " before this star gets removed");
				Bukkit.broadcastMessage(ChatColor.GOLD + "[StarHunter] " + ChatColor.RED + user + ChatColor.YELLOW + " has reached the stars core! There is " + ChatColor.RED + temp + " " + tim + ChatColor.YELLOW + " until the next hunt begins.");
				
				messages = 0;
				
				timer =  Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
					public void run(){
						int msg1 = (getConfig().getInt("Cooldown Timer"))/20;
						int msg2 = (getConfig().getInt("Cooldown Timer")* 2/3)/20;
						int msg3 = (getConfig().getInt("Cooldown Timer")* 1/3)/20;
						String time;
				    	switch(messages){
				   
				    		case 0:
				    			fallingblocks.removal();
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
				    			Bukkit.getScheduler().cancelTask(timer);
				    
				    		messages = 0;
				    			
				    			fallingblocks.loc();
				    			break;
				    	
				    	}
				 
				    }
				    
				}, getConfig().getInt("Mining Time"), getConfig().getInt("Cooldown Timer")/3);
				
				
			}else if(randoms == true){
			
				world.getBlock().setType(Material.AIR);
				
				Random rand = new Random();
				 Material[] materiallists = Material.values();
				 
				 
				 int size = materiallists.length;
				 int index = rand.nextInt(size);
				 
				 
				 Material randomMaterial = materiallists[index];
				 ItemStack item = new ItemStack(randomMaterial);
				 Material items = item.getType();
				 
				 p.getWorld().dropItemNaturally(world.getBlock().getLocation(), new ItemStack(items));
				
				
			}else{
				world.getBlock().setType(Material.AIR);
				try { 
					
					for(String bob: StarHunter.list){
						 p.getWorld().dropItemNaturally(world.getBlock().getLocation(), new ItemStack(Material.getMaterial(bob)));
					 }
					
				} catch (NullPointerException ex) {
				    System.out.println("StarHunter configuration error, invalid items!");
				    randoms = true;
				   }
				
			}
	
		}
		}
	
	
	}
