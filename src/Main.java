
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.egl.KHRDebug;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

import graphics.Mesh;
import graphics.Shader;


public class Main {
	static long window;
	private static int WIN_WIDTH = 500;
	private static int WIN_HEIGHT = 500;
	
	public static void main(String[] args) {
		// glfwInit
		if (!glfwInit()) {
			throw new IllegalStateException("Error for glfwInit");
		}
		
		// Hint 6
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR,3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR,2);

		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT,GLFW_TRUE);
		
		glfwWindowHint( GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE );

		
		glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
		
		
		// Window
		// window create
		window = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT, "warmulfollowalong", 0, 0);
		// window check
		if  (window == 0) {
			throw new IllegalStateException("Error creating window");
		}
		
		// window CONTEXT and gl capabilities
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		// debugger
		glEnable(org.lwjgl.opengl.KHRDebug.GL_DEBUG_OUTPUT_SYNCHRONOUS);
		// store this in a field somewhere so it doesn't gets GC'd
		Callback callbackA = GLUtil.setupDebugMessageCallback();
		
		
		
		// window position
		GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, videoMode.width()/2-WIN_WIDTH/2, videoMode.height()/2-WIN_HEIGHT/2);
		
		// window display
		glfwShowWindow(window);
		
		
		
		// CREATE MESH
		Mesh testMesh = new Mesh();
		testMesh.create(new float[] {
				-1, -1, 0,
				0,1,0,
				1,-1,-1,
		});
		
		
		// Create shader
		Shader shader = new Shader();
		shader.create("shader");
		
		
		// LOOP
		while(!glfwWindowShouldClose(window)) {
			glfwPollEvents();
			
			// clear
			glClearColor(1, 0, 0, 1);
			glClear(GL_COLOR_BUFFER_BIT);
			
			// shader
			shader.useShader();
			
			// DRAW
			testMesh.draw(); 
			
			glfwSwapBuffers(window);
		}

		// destroy
		testMesh.destroy();
		shader.destroy();
		
		// close
		glfwTerminate();
	}
}
