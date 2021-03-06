package at.tyron.vintagecraft.World;

import java.util.HashMap;

import at.tyron.vintagecraft.VintageCraft;
import at.tyron.vintagecraft.WorldGen.Noise.SimplexNoise;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WindGen {
	private static HashMap<World, WindGen> windgens = new HashMap<World, WindGen>();
	
	SimplexNoise windGen;
	World world;
	
	public static void registerWorld(World world) {
		WindGen gen = new WindGen(world.getSeed(), world);
		windgens.put(world, gen);
	}
	
	public static void unregisterWorld(World world) {
		windgens.remove(world);
	}
	
	public static WindGen getWindGenForWorld(World world) {
		return windgens.get(world);
	}
	
	private WindGen(long seed, World world) {
		int octaves = 4;
		float persistence = 0.8f;
		
		windGen = new SimplexNoise(octaves, persistence, seed);
		
		this.world = world;
	}
	
	
	public double getWindAt(BlockPos pos) {
		//windGen = new SimplexNoise(4, 0.8, world.getSeed());
		
		return windGen.getNoise(
			pos.getX() / 2048f, 
			world.getTotalWorldTime() / 8192f, 
			pos.getZ() / 2048f
		);
	}
	
	
}
