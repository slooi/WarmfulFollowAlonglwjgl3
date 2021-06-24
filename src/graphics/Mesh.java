package graphics;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


public class Mesh {
	private int vao;
	private int vbo;
	
	private int vertexCount;
	
	public void Mesh() {
		
	}
	
	public boolean create(float[] vertices) {	// kinda don't like how he uses float[]
		// Create VAO and bind it
		vao = glGenVertexArrays();
		glBindVertexArray(vao);

		// Create empty buffer and bind it and bufferData
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER,vertices,GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0,0);
		
		// Unbind VAO
		glBindVertexArray(0);
		
		// Set vertexCount
		vertexCount = vertices.length / 3;
		
		return true;
	}
	
	public void destroy() {
		// delete vao & vbo
		glDeleteVertexArrays(vao);
		glDeleteBuffers(vbo);
	}
	
	public void draw() {	// Seems very inefficient
		// bind VAO
		glBindVertexArray(vao);
		// enable VBO (essentially) / enable vertex Attribute Array
		glEnableVertexAttribArray(0);	// !@$!@#!@# when to use???
		
		// DRAW
		glDrawArrays(GL_TRIANGLES, 0, vertexCount);

		// disable VBO (essentially) / enable vertex Attribute Array
		glDisableVertexAttribArray(0);
		// unbind VAO
		glBindVertexArray(0);
	}
}
