package graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL11.GL_FALSE;	// have to use static
import static org.lwjgl.opengl.GL20.*;

public class Shader {
	// stores int handle
	private int vertexShader, fragmentShader, program;
	
	public Shader(){
				
	}
	
	public boolean create(String shader) {
		// Create vertex shader
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, readSource(shader+".vs"));  //<!@#!@#!@#  shader + ".vs"
		glCompileShader(vertexShader);
		
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
			throw new Error("ERROR: compiling vertex shader. Info: "+glGetShaderInfoLog(vertexShader));
		}
		
		// Create fragment shader
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader,readSource(shader+".fs"));	  //<!@#!@#!@#  shader + ".fs"
		glCompileShader(fragmentShader);
		
		if(glGetShaderi(fragmentShader,GL_COMPILE_STATUS) == GL_FALSE) {
			throw new Error("ERROR: compiling fragment shader. Info: "+glGetShaderInfoLog(fragmentShader));
		}
		
		// Program
		program = glCreateProgram();
		System.out.println("init program"+program);
		glAttachShader(program, vertexShader);	// !@#!@#!@#
		glAttachShader(program, fragmentShader);
		
		// Program check
		glLinkProgram(program);
		if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
			throw new Error("ERROR: linking program. Info: "+glGetProgramInfoLog(program));
		}
		glValidateProgram(program);
		if (glGetProgrami(program,GL_VALIDATE_STATUS) == GL_FALSE) {
			throw new Error("ERROR: validating program. Info: "+glGetProgramInfoLog(program));
		}
		System.out.print(glGetProgrami(program, GL_LINK_STATUS));
		
		
		return true;	// !@#!@# Shouldn't you be passing down the program? Isn't this like an anti pattern?
		
	}
	
	public void destroy() {
		// detach
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);
		
		// delete shaders
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
	
		// delete program
		glDeleteProgram(program);
	}
	
	public void useShader() {

//		System.out.println("useShader program"+program);
		glUseProgram(program);
	}
	
	public String readSource(String file) {
		BufferedReader reader = null;
		StringBuilder sourceBuilder = new StringBuilder();
		
		try {
			reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/shaders/"+file)));

			String line;
			
			while((line = reader.readLine()) != null) {
				sourceBuilder.append(line+"\n");	//<= remember to add new line
			}
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				//MY
				if(reader != null){
					reader.close();
				}
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		return sourceBuilder.toString();
	}
	
}
