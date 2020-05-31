package sk.pa3kc.shaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader implements AutoCloseable {
    public static enum Type {
        GL_VERTEX_SHADER(GL20.GL_VERTEX_SHADER),
        GL_FRAGMENT_SHADER(GL20.GL_FRAGMENT_SHADER);

        public final int index;
        private Type(int index) {
            this.index = index;
        }
    }

    private int shaderId;

    public int getShaderId() {
        return this.shaderId;
    }

    protected void loadShader(File file, Type type) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException(file.getPath() + " does not exists");
        }

        final StringBuilder builder = new StringBuilder();

        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

        this.shaderId = GL20.glCreateShader(type.index);
        GL20.glShaderSource(shaderId, builder.toString());
        GL20.glCompileShader(shaderId);

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) != GL11.GL_TRUE) {
            System.err.println(GL20.glGetShaderInfoLog(shaderId));
            System.err.println("Could not compile shader");
            System.exit(-1);
        }
    };

    @Override
    public void close() {
        GL20.glDeleteShader(this.shaderId);
    }

    public abstract void loadShader(File file);
}