package entityDAO;

import java.io.File;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.*;

import entity.*;

public class SiteDAO{
	
/*	EntityManagerFactory factory = Persistence.createEntityManagerFactory("db_assign5");
	EntityManager em = null;
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Site findSite(@PathParam("id") int siteId){
		Site site = null;
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
	
		site = em.find(Site.class, siteId);
		
		em.getTransaction().commit();
		em.close();
		
		return site;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)

	public List<Site> findAllSites() {
		List<Site> sites = new ArrayList<Site>();
		
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createNamedQuery("findAllSites");
		sites = query.getResultList();
		
		em.getTransaction().commit();
		em.close();

		return sites;
	}  */
	
	public void exportSiteDatabaseToXmlFile(List<Site> sites, String xmlFileName){
		try{
			JAXBContext jaxb = JAXBContext.newInstance(SiteList.class);
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.marshal(sites, new File(xmlFileName));
		}
		catch(JAXBException e){
			e.printStackTrace();
		}
	}
	
	public void convertXmlFileToOutputFile(String inputXmlFileName, String outputXmlFileName, String xsltFileName){
	File inputfile= new File(inputXmlFileName);
	File xsltfile = new File(xsltFileName);
	File outputfile = new File(outputXmlFileName);
	
	StreamSource inputSource = new StreamSource(inputfile);
	StreamSource xsltSource = new StreamSource(xsltfile);
	StreamResult outputSource = new StreamResult(outputfile);
	
	TransformerFactory factory = TransformerFactory.newInstance();
	Transformer transformer;
	try {
		transformer = factory.newTransformer(xsltSource);
		transformer.transform(inputSource, outputSource);
	} catch (TransformerConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	catch (TransformerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

public static void main(String args[]){
	SiteDAO dao = new SiteDAO();
	
	Site site = new Site();
	site.setName("abc");
	
	List<Site> sites = null;
	sites.add(site);
	((SiteList) sites).setSites(sites);
	
	dao.exportSiteDatabaseToXmlFile(sites,"sites.xml");
	dao.convertXmlFileToOutputFile("site.xml", "selected.xml", "sites2selected.xsl" );
}
}
	
	