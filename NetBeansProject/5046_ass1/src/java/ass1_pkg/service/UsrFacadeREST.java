/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass1_pkg.service;

import ass1_pkg.Usr;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;



/**
 *
 * @author 42901
 */
@Stateless
@Path("ass1_pkg.usr")
public class UsrFacadeREST extends AbstractFacade<Usr> {

    @PersistenceContext(unitName = "5046_ass1PU")
    private EntityManager em;

    public UsrFacadeREST() {
        super(Usr.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usr entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Usr entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Usr find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usr> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usr> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @GET
        @Path("findByFirstName/{firstName}")
        @Produces({"application/json"})
        public List<Usr> findByFirstName(@PathParam("firstName") String firstName) {
            Query query = em.createNamedQuery("Usr.findByFirstName");
            query.setParameter("firstName",firstName);
            return query.getResultList();
        }
        
        @GET
        @Path("findBySurname/{surname}")
        @Produces({"application/json"})
        public List<Usr> findBySurname(@PathParam("surname") String surname) {
            Query query = em.createNamedQuery("Usr.findBySurname");
            query.setParameter("surname",surname);
            return query.getResultList();
        }
        
    @GET
        @Path("findByEmail/{email}")
        @Produces({"application/json"})
        public List<Usr> findByEmail(@PathParam("email") String email) {
            Query query = em.createNamedQuery("Usr.findByEmail");
            query.setParameter("email",email);
            return query.getResultList();
        }
       
         @GET
        @Path("findByDob/{dob}")
        @Produces({"application/json"})
        public List<Usr> findByDob(@PathParam("dob") String dob) {
            Query query = em.createNamedQuery("Usr.findByDob");
        try {
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date   birthdate = format.parse ( dob );  
            query.setParameter("dob",birthdate);
              
           
        } catch (ParseException ex) {
            Logger.getLogger(UsrFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
         return query.getResultList();
        }
        
        @GET
        @Path("findByHeight/{height}")
        @Produces({"application/json"})
        public List<Usr> findByHeight(@PathParam("height") double height) {
            Query query = em.createNamedQuery("Usr.findByHeight");
            query.setParameter("height",height);
            return query.getResultList();
        }
        
        @GET
        @Path("findByWeight/{weight}")
        @Produces({"application/json"})
        public List<Usr> findByWeight(@PathParam("weight") double weight) {
            Query query = em.createNamedQuery("Usr.findByWeight");
            query.setParameter("weight",weight);
            return query.getResultList();
        }
        
        @GET
        @Path("findByGender/{gender}")
        @Produces({"application/json"})
        public List<Usr> findByGender(@PathParam("gender")  String gender) {
            Query query = em.createNamedQuery("Usr.findByGender");
            char genCh = gender.charAt(0);
            query.setParameter("gender",genCh);
            return query.getResultList();
        }
        
        @GET
        @Path("findByAddress/{address}")
        @Produces({"application/json"})
        public List<Usr> findByAddress(@PathParam("email") String address) {
            Query query = em.createNamedQuery("Usr.findByAddress");
            query.setParameter("address",address);
            return query.getResultList();
        }
        
        @GET
        @Path("findByPostcode/{postcode}")
        @Produces({"application/json"})
        public List<Usr> findByPostcode(@PathParam("postcode") String postcode) {
            Query query = em.createNamedQuery("Usr.findByPostcode");
            query.setParameter("postcode",postcode);
            return query.getResultList();
        }
        
        @GET
        @Path("findByActivityLevel/{activityLevel}")
        @Produces({"application/json"})
        public List<Usr> findByActivityLevel(@PathParam("activityLevel") int activityLevel) {
            Query query = em.createNamedQuery("Usr.findByActivityLevel");
            query.setParameter("activityLevel",activityLevel);
            return query.getResultList();
        }
        
        @GET
        @Path("findByStepsMile/{stepsMile}")
        @Produces({"application/json"})
        public List<Usr> findByStepsMile(@PathParam("stepsMile") int stepsMile) {
            Query query = em.createNamedQuery("Usr.findByStepsMile");
            query.setParameter("stepsMile",stepsMile);
            return query.getResultList();
        }
        
        
        @GET
        @Path("findByWeightAndHeight/{weight}/{height}")
        @Produces({"application/json"})
        public List<Usr> findByWeightAndHeight ( @PathParam("weight") double weight,  @PathParam("height") double height) {
            TypedQuery <Usr> q = em.createQuery("SELECT s FROM Usr s WHERE s.weight = :weight AND s.height = :height",Usr.class);
            q.setParameter("weight", weight);
            q.setParameter("height", height);
            return q.getResultList();
}     
       
        @GET
        @Path("calorieBurnedPerStep/{userId}")
        @Produces({"application/json"})
        public String calorieBurnedPerStep ( @PathParam("userId") Integer userId) {
         TypedQuery <Usr> q = em.createQuery("SELECT s FROM Usr s WHERE s.userId = :userId",Usr.class);   
         q.setParameter("userId",userId);
         Usr people = q.getSingleResult();
         double w = people.getWeight();
         double steps = new Double(people.getStepsMile());
         return String.valueOf(w*2.2*0.49/steps);

        }
        
        @GET
        @Path("BMR/{userId}")
        @Produces({"application/json"})
        public BigDecimal BMR ( @PathParam("userId") Integer userId) {
         TypedQuery <Usr> q = em.createQuery("SELECT s FROM Usr s WHERE s.userId = :userId",Usr.class);   
         q.setParameter("userId",userId);
         Usr people = q.getSingleResult();
         double w = people.getWeight();
         double h = people.getHeight();
         Date bd = people.getDob();
         Date now = new Date();
         DateFormat formatter = new SimpleDateFormat("yyyyMMdd");                           
    int d1 = Integer.parseInt(formatter.format(bd));                            
    int d2 = Integer.parseInt(formatter.format(now));                          
    int age = (d2 - d1) / 10000;   
         
         char gend = people.getGender();
         double BMR = 0;
         if (gend == 'M') {
         
             BMR = 13.75*w + 5.003*h - 6.755*age + 66.5;
         }
         else {
             BMR = 9.563*w + 1.85*h  -4.676*age +655.1;
         }
       
         return BigDecimal.valueOf(BMR);
            
        }
        
        
       @GET
        @Path("dailyCaloryBurn/{userId}")
        @Produces({"application/json"}) 
       public BigDecimal dailyCaloryBurn(@PathParam("userId") Integer userId) {
          double BMR = BMR(userId).doubleValue();
          TypedQuery <Usr> q = em.createQuery("SELECT s FROM Usr s WHERE s.userId = :userId",Usr.class);   
         q.setParameter("userId",userId);
         Usr people = q.getSingleResult();
         int level = people.getActivityLevel();
        double dailyBurn = 0;
          switch (level) {
              case 1 : dailyBurn = 1.2*BMR; break;
              case 2 : dailyBurn = 1.375*BMR; break;
              case 3 : dailyBurn = 1.55*BMR; break;
              case 4 : dailyBurn = 1.725*BMR; break;
              case 5 : dailyBurn = 1.9*BMR; break;
              default: break;
          }
          return BigDecimal.valueOf(dailyBurn);
       }
        
    
        
}
