package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		RawModel tree = OBJLoader.loadObjModel("lowPolyTree", loader);
		RawModel fern = OBJLoader.loadObjModel("fern", loader);
		RawModel grass = OBJLoader.loadObjModel("grassModel", loader);

		TexturedModel treeTx = new TexturedModel(tree,new ModelTexture(loader.loadTexture("lowPolyTree")));
		TexturedModel fernTx = new TexturedModel(fern,new ModelTexture(loader.loadTexture("fern")));
		TexturedModel grassTx = new TexturedModel(grass,new ModelTexture(loader.loadTexture("grassTexture")));
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for(int i=0;i<800;i++){
			entities.add(new Entity(treeTx, new Vector3f(random.nextFloat()*800 ,0,random.nextFloat() * 800 ),0,0,0,0.5f));
			entities.add(new Entity(fernTx, new Vector3f(random.nextFloat()*800 ,0,random.nextFloat() * 800 ),0,0,0,1));
			entities.add(new Entity(grassTx, new Vector3f(random.nextFloat()*800 ,0,random.nextFloat() * 800 ),0,0,0,1));
		}
		
		Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));

		Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));




		
		Camera camera = new Camera();
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested()){
			camera.move();

			renderer.processTerrain(terrain);

			for(Entity entity:entities){
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}
