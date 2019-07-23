/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass1_pkg.service;

import ass1_pkg.Consumption;
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
@Path("ass1_pkg.consumption")
public class ConsumptionFacadeREST extends AbstractFacade<Consumption> {

    @PersistenceContext(unitName = "5046_ass1PU")
    private EntityManager em;

    public ConsumptionFacadeREST() {
        super(Consumption.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Consumption entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Consumption entity) {
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
    public Consumption find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("findByConsumDate/{consumDate}")
    @Produces({"application/json"})
    public List<Consumption> findByConsumDate(@PathParam("consumDate") String consumDate) {
            Query query = em.createNamedQuery("Consumption.findByConsumDate");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date conDate = format.parse ( consumDate );  
                query.setParameter("consumDate",conDate);
            } catch (Exception ex) {
            Logger.getLogger(ConsumptionFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
            return query.getResultList();
        }
        
    @GET
        @Path("findByQuantity/{quantity}")
        @Produces({"application/json"})
        public List<Consumption> findByQuantity(@PathParam("quantity") double quantity){
            Query query = em.createNamedQuery("Consumption.findByQuantity");
            query.setParameter("quantity",quantity );
            return query.getResultList();
        }
        
    @GET
        @Path("findByFoodNameAndQuantity/{foodName}/{quantity}")
        @Produces({"application/json"})
        public List<Consumption> findByFoodName(@PathParam("foodName") String foodName, @PathParam("quantity") double quantity) {
            TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.foodId.foodName = :foodName AND s.quantity = :quantity", Consumption.class);
            q.setParameter("foodName", foodName);
            q.setParameter("quantity", quantity);
             return q.getResultList();
}
        
    @GET
        @Path("findByCategoryAndQuantity/{quantity}/{category}")
        @Produces({"application/json"})
        public List<Consumption> findByCategoryAndQuantity (@PathParam("quantity") double quantity, @PathParam("category") String category )  {
        Query query = em.createNamedQuery("Consumption.findByCategoryAndQuantity");
        query.setParameter("quantity",quantity);
        query.setParameter("category",category);
         return query.getResultList();
    }
        
       @GET
       @Path("findByUserId/{userId}")
        @Produces({"application/json"})
        public List<Consumption> findByUserId(@PathParam("userId") Integer userId) {
            TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.userId.userId = :userId",Consumption.class);
            q.setParameter("userId", userId);
            return q.getResultList();
}
        
        @GET
       @Path("findByFoodId/{foodId}")
        @Produces({"application/json"})
        public List<Consumption> findByFoodId(@PathParam("foodId") Integer foodId) {
            TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.foodId.foodId = :foodId",Consumption.class);
            q.setParameter("foodId", foodId);
            return q.getResultList();
}
        
        @GET
        @Path("caloryConsumeThatDay/{consumDate}/{userId}")
        @Produces({"application/json"})
        public String caloryConsumeThatDay(@PathParam("consumDate") String consumDate,@PathParam("userId") Integer userId ) {
            TypedQuery<Consumption> q = em.createQuery("SELECT s FROM Consumption s WHERE s.consumDate = :consumDate AND s.userId.userId = :userId",Consumption.class);
            try {
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date   d = format.parse ( consumDate );  
            q.setParameter("consumDate",d);          
        } catch (ParseException ex) {
            Logger.getLogger(UsrFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
            q.setParameter("userId", userId);
            List<Consumption> consumList = q.getResultList();
            double sum = 0;
            for(Consumption c : consumList) {
            sum = sum + c.getQuantity() * c.getFoodId().getCalorieAmt();
        }
            return String.valueOf(sum);
        }
        
        
    
}
