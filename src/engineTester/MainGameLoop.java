package engineTester;

import Models.TexturedModel;
import entities.Camera;
import entities.Entity;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import Models.RawModel;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {

  public static void main(String[] args) {

    DisplayManager.creteDisplay();
    Loader loader = new Loader();
    StaticShader shader = new StaticShader();
    Renderer renderer = new Renderer(shader);

    RawModel model = OBJLoader.loadOBJModel("mann", loader);

    TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("image")));

    Entity entity = new Entity(staticModel, new Vector3f(0,0,-10),0,0,0,1);

    Camera camera = new Camera();

    while(!Display.isCloseRequested()){
      entity.increaseRotation(0, 1, 0);
      camera.move();
      renderer.prepare();
      shader.start();
      shader.loadViewMatrix(camera);
      renderer.render(entity,shader);
      shader.stop();
      DisplayManager.updateDisplay();
    }

    shader.cleanUp();
    loader.cleanUp();
    DisplayManager.closeDisplay();

  }

}
