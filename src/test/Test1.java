package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Test1 {
	
	  @Before
	  public void initialiser() throws Exception {
	    //personne = new Personne("nom1","prenom1");
	  }

	  @After
	  public void nettoyer() throws Exception {
	    //personne = null;
	  }
	  
	  @Test
	  public void personne() {
	    //assertNotNull("L'instance n'est pas créée", personne);
	  }

	  

}
