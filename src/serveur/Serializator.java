/*package serveur;
import java.lang.reflect.Field;



import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Serializator {
	
	static Serializator S;
	
	private Serializator()
	{
		
	}
	
	static public Serializator getSerializator()
	{
		if(S != null )
			return S;
		else{
			S = new Serializator();
			return S;
		}
	}
	
	void print (String s)
	{
		System.out.println(s);
	}
	
    static public Object Deserialise(String s) throws ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		JSONParser json = new JSONParser();
		
		Object parsedObject = json.parse(s);
		
		JSONObject jobj = (JSONObject)parsedObject;
		
		String ClassName = (String)jobj.get("ClassType");
		jobj.remove("ClassType");
		Class classDef = Class.forName(ClassName);
		Object object = classDef.newInstance();
		
		Class<?> clazz = object.getClass();
		
		for(Field field : clazz.getDeclaredFields()) 
		{
			try
			{
				
				String name = field.getName();
				String value = (String) jobj.get(name);
				field.setAccessible(true);
				Object fieldType = field.get(object);
				
				Object type = fieldType.getClass();
				
				Object corrected = null;
				
				if(type == byte.class)
				{
					corrected = (byte)Byte.parseByte(value);
				}
				else if(type == short.class)
				{
					corrected = (short)Short.parseShort(value);
				}
				else if(type == int.class)
				{
					corrected = (int)Integer.parseInt(value);
				}
				else if(type == long.class)
				{
					corrected = (long)Long.parseLong(value);
				}
				else if(type == float.class)
				{
					corrected = (float)Float.parseFloat(value);
				}
				else if(type == double.class)
				{
					corrected = (double)Double.parseDouble(value);
				}
				else if(type == char.class)
				{
					corrected = value.charAt(0); 
				}
				else if(type == String.class)
				{
					corrected = value;
				}
				else if(type == boolean.class)
				{
					corrected = (boolean)Boolean.getBoolean(value);
				}
				
				field.set(object, corrected);
			}
			catch(Exception e)
			{
				//not primitive
			}
		}
		
		
		
		return object;
	}
	
    static public String SerialiseAll(Object o) throws IllegalArgumentException, IllegalAccessException  
	{
		Class<?> clazz = o.getClass();
		JSONObject json = new JSONObject();
		
		String ClassName = clazz.getName();
		json.put("ClassType", ClassName);
		
		for(Field field : clazz.getDeclaredFields()) 
		{
			try
			{
		       //you can also use .toGenericString() instead of .getName(). This will
		       //give you the type information as well.
			   String name = field.getName();
			   field.setAccessible(true);
			   Object value = field.get(o);
			   
			   json.put(name, value.toString());
		       
		       
			}
			catch(Exception e)
			{
				//print(json.toJSONString());
				//print(e.getMessage());
			}
		}		
		
	
		return json.toJSONString();
	}
	
	
	
	
	//junk for demo
	public static void showFields(Object o) throws IllegalArgumentException, IllegalAccessException {
		   Class<?> clazz = o.getClass();
		   
		   try{
			   lol l = new lol();
			   Field f = clazz.getDeclaredField("a");
			   f.setAccessible(true);
			   Object a  = f.get(l);
			   System.out.println( a.toString());
			   
		   }
		   catch(Exception e){
			   System.out.println( "less bad");
		   }
		   /*
		   for(Field field : clazz.getDeclaredFields()) {
		       //you can also use .toGenericString() instead of .getName(). This will
		       //give you the type information as well.

		       System.out.println(field.getName());
		   }/
		}
}
*/