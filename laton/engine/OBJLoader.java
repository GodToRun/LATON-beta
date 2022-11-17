package laton.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import laton.engine.Obj.Face;
import vecutils.Vector2;
import vecutils.Vector3;
import vecutils.Vector4;
public class OBJLoader extends Mesh {
	Sector currentSector;
	public Obj model;
	public OBJLoader(String filepath) {
		super(GL11.GL_TRIANGLES);
		colors = new Vector4[1];
		colors[0] = new Vector4(1f, 1f, 1f, 1f);
		
		try {
			model = loadModel(new File(filepath));
			/*for (Sector sec : model.sectors) {
				System.out.println("NAME: " + sec.name + ", MIN (" + sec.min.x + ", " + sec.min.y + ", " + sec.min.z + ")" + ", MAX(" + sec.max.x + ", " + sec.max.y + ", " + sec.max.z + ")");
			}*/
			vertices = new Vector3[model.getFaces().size() * 3];
			uvs = new Vector2[vertices.length];
			int i = 0;
			for (Obj.Face face : model.getFaces()) {
	            Vector3f[] normals = {
	                model.getNormals().get(face.getNormals()[0] - 1),
	                model.getNormals().get(face.getNormals()[1] - 1),
	                model.getNormals().get(face.getNormals()[2] - 1)
	            };
	            Vector2f[] texCoords = {
	                model.getTextureCoordinates().get(face.getTextureCoords()[0] - 1),
	                model.getTextureCoordinates().get(face.getTextureCoords()[1] - 1),
	                model.getTextureCoordinates().get(face.getTextureCoords()[2] - 1)
	            };
	            Vector3[] fvertices = {
	                model.getVertices().get(face.getVertices()[0] - 1),
	                model.getVertices().get(face.getVertices()[1] - 1),
	                model.getVertices().get(face.getVertices()[2] - 1)
	            };
	            	
	            	vertices[i+0] = fvertices[0];
	            	uvs[i+0] = new Vector2(texCoords[0].getX(), texCoords[0].getY());
	            	vertices[i+1] = fvertices[1];
	            	uvs[i+1] = new Vector2(texCoords[1].getX(), texCoords[1].getY());
	            	vertices[i+2] = fvertices[2];
	            	uvs[i+2] = new Vector2(texCoords[2].getX(), texCoords[2].getY());
	            	
	            	i += 3;
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
    }
	public Sector findSector(String name) {
		for (Sector sec : model.sectors) {
			if (sec.name.equals(name)) {
				return sec; 
			}
		}
		return null;
	}
    /**
     * @param file the file to be loaded
     * @return the loaded <code>Obj</code>
     * @throws java.io.FileNotFoundException thrown if the Obj file is not found
     */
    public Obj loadModel(File file) throws FileNotFoundException {
        return this.loadModel(new Scanner(file));
    }

    /**
     * @param stream the stream to be loaded
     * @return the loaded <code>Obj</code>
     */
    public Obj loadModel(InputStream stream) {
        return this.loadModel(new Scanner(stream));
    }
    void calcMinMax(Vector3 vtx) {
    	if (currentSector == null) return;
    	/*if (currentSector.min.x > vtx.x) {
    		currentSector.min.x = vtx.x;
    	}
    	else if (currentSector.min.y > vtx.y) {
    		currentSector.min.y = vtx.y;
    	}
    	else if (currentSector.min.z > vtx.z) {
    		currentSector.min.z = vtx.z;
    	}
    	
    	if (currentSector.max.x < vtx.x) {
    		currentSector.max.x = vtx.x;
    	}
    	else if (currentSector.max.y < vtx.y) {
    		currentSector.max.y = vtx.y;
    	}
    	else if (currentSector.max.z < vtx.z) {
    		currentSector.max.z = vtx.z;
    	}*/
    	
    	currentSector.min.x = Math.min(currentSector.min.x, vtx.x);
    	currentSector.min.y = Math.min(currentSector.min.y, vtx.y);
    	currentSector.min.z = Math.min(currentSector.min.z, vtx.z);
    	
    	currentSector.max.x = Math.max(currentSector.max.x, vtx.x);
    	currentSector.max.y = Math.max(currentSector.max.y, vtx.y);
    	currentSector.max.z = Math.max(currentSector.max.z, vtx.z);
    	
    	
    }

    /**
     * @param sc the <code>Obj</code> to be loaded
     * @return the loaded <code>Obj</code>
     */
    public Obj loadModel(Scanner sc) {
        Obj model = new Obj();
        while (sc.hasNextLine()) {
            String ln = sc.nextLine();
            if (ln == null || ln.equals("") || ln.startsWith("#")) {
            } else {
                String[] split = ln.split(" ");
                switch (split[0]) {
                    case "v":
                    	Vector3 vf = new Vector3(
                                Float.parseFloat(split[1]),
                                Float.parseFloat(split[2]),
                                Float.parseFloat(split[3]));
                        model.getVertices().add(vf);
                        if (currentSector != null) {
                        	if (currentSector.offset == -9999)
                        		currentSector.offset = model.getVertices().size()-1;
                        	currentSector.vecs.add(vf);
                        	calcMinMax(new Vector3(vf.x, vf.y, vf.z));
                        }
                        break;
                    case "vn":
                        model.getNormals().add(new Vector3f(
                                Float.parseFloat(split[1]),
                                Float.parseFloat(split[2]),
                                Float.parseFloat(split[3])
                        ));
                        break;
                    case "o":
                    	currentSector = new Sector();
                    	currentSector.refAABB = new AABB(Vector3.ZERO(), Vector3.ZERO());
                    	currentSector.name = split[1];
                    	model.sectors.add(currentSector);
                    	break;
                    case "vt":
                        model.getTextureCoordinates().add(new Vector2f(
                                Float.parseFloat(split[1]),
                                Float.parseFloat(split[2])
                        ));
                        break;
                    case "f":
                        model.getFaces().add(new Obj.Face(
                                new int[]{
                                    Integer.parseInt(split[1].split("/")[0]),
                                    Integer.parseInt(split[2].split("/")[0]),
                                    Integer.parseInt(split[3].split("/")[0])
                                },
                                new int[]{
                                    Integer.parseInt(split[1].split("/")[1]),
                                    Integer.parseInt(split[2].split("/")[1]),
                                    Integer.parseInt(split[3].split("/")[1])
                                },
                                new int[]{
                                    Integer.parseInt(split[1].split("/")[2]),
                                    Integer.parseInt(split[2].split("/")[2]),
                                    Integer.parseInt(split[3].split("/")[2])
                                }
                        ));
                        break;
                    case "s":
                        model.setSmoothShadingEnabled(!ln.contains("off"));
                        break;
                    default:
                        System.err.println("[OBJ] Unknown Line: " + ln);
                }
            }
        }
        sc.close();
        return model;
    }
}