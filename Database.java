import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Database{

	final String db_file = "serialized_db.db";

	public Database(){
		initDB();
	}
	HashMap<String,Product> getProducts(){
		try {
			FileInputStream fis = new FileInputStream(db_file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, Product> products = new HashMap<String, Product>();
			products = (HashMap<String, Product>) ois.readObject();
			ois.close();
			fis.close();
			return products;
        }catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	boolean deleteProduct(String productId){
		try {
			
			FileInputStream fis = new FileInputStream(db_file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, Product> products = new HashMap<String, Product>();
			products = (HashMap<String, Product>) ois.readObject();
			ois.close();
			fis.close();
			products.remove(productId);
			Product pd = products.get(productId);

			products.put(pd.getId(), pd);

			FileOutputStream fos = new FileOutputStream(db_file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(products);
			oos.close();
			fos.close();
				
		} catch (Exception e) {
			System.out.println("Error deleting product"+e);
		}
		return false;
	}
	Product buyProduct(String productId, int many){

		try {
			FileInputStream fis = new FileInputStream(db_file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, Product> products = new HashMap<String, Product>();
			products = (HashMap<String, Product>) ois.readObject();
			ois.close();
			fis.close();
			Product pd = products.get(productId);

			if(pd.getStock()-many >= 0){
				pd.setStock(pd.getStock() - many);
				products.put(pd.getId(), pd);

				FileOutputStream fos = new FileOutputStream(db_file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				
				oos.writeObject(products);
				oos.close();
				fos.close();
			}

			return pd;
        }catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	Product restockProduct(String productId, int many){

		try {
			FileInputStream fis = new FileInputStream(db_file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, Product> products = new HashMap<String, Product>();
			products = (HashMap<String, Product>) ois.readObject();
			ois.close();
			fis.close();
			Product pd = products.get(productId);

			if(pd.getStock()+many >= 0){
				pd.setStock(pd.getStock() + many);
				products.put(pd.getId(), pd);

				FileOutputStream fos = new FileOutputStream(db_file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(products);

				oos.close();
				fos.close();
				
			}

			return pd;
        }catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	public String printProducts(){

		ArrayList<Product> a = new ArrayList<Product>(getProducts().values());
		String out = "";
		for(int i = 0; i < a.size(); i++) {
			out = out + (a.get(i).toString());
			out = out + ("\n");
		}
		return out;

	}
	public void addProduct(Product p){
		try{
			FileInputStream fis = new FileInputStream(db_file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			HashMap<String, Product> products = new HashMap<String, Product>();
			
			products = (HashMap<String, Product>) ois.readObject();
			
			ois.close();
			fis.close();
			
			FileOutputStream fos = new FileOutputStream(db_file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			products.put(p.getId(),p);
			oos.writeObject(products);
			

			oos.close();
			fos.close();

		}catch(Exception e){
			System.out.println("Error adding product\n");
			e.printStackTrace();
		}

	}
	private void initDB(){
		try{
			boolean fileExists = new File(db_file).exists();
			if(!fileExists){
				FileOutputStream fos = new FileOutputStream(db_file);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				HashMap<String, Product> products = new HashMap<String, Product>();
				oos.writeObject(products);
				
				oos.close();
				fos.close();
				System.out.println("Database created");
			}
		}catch(Exception e){
			System.out.println("Error creating Database\n"+e);
		}
	}
}
