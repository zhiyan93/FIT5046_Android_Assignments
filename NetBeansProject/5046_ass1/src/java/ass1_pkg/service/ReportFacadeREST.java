/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ass1_pkg.service;

import ass1_pkg.Report;
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
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.TypedQuery;
/**
 *
 * @author 42901
 */
@Stateless
@Path("ass1_pkg.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "5046_ass1PU")
    private EntityManager em;

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
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
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
        @Path("findByReportDate/{reportDate}")
        @Produces({"application/json"})
        public List<Report> findByReportDate(@PathParam("reportDate") String reportDate) {
            Query query = em.createNamedQuery("Report.findByReportDate");
        try {
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            Date   repdate = format.parse ( reportDate );  
            query.setParameter("reportDate",repdate);
              
           
        } catch (ParseException ex) {
            Logger.getLogger(UsrFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
         return query.getResultList();
        }
        
        @GET
        @Path("findByCalConsum/{calConsum}")
        @Produces({"application/json"})
        public List<Report> findByCalConsum(@PathParam("calConsum") double calConsum) {
            Query query = em.createNamedQuery("Report.findByCalConsum");
            query.setParameter("calConsum",calConsum);
            return query.getResultList();
        }
        
        @GET
        @Path("findByCalBurned/{calBurned}")
        @Produces({"application/json"})
        public List<Report> findByCalBurned(@PathParam("calBurned") double calBurned) {
            Query query = em.createNamedQuery("Report.findByCalBurned");
            query.setParameter("calBurned",calBurned);
            return query.getResultList();
        }
        
        @GET
        @Path("findBySteps/{steps}")
        @Produces({"application/json"})
        public List<Report> findBySteps(@PathParam("steps") int steps) {
            Query query = em.createNamedQuery("Report.findBySteps");
            query.setParameter("steps",steps);
            return query.getResultList();
        }
        
        @GET
        @Path("findByCalGoal/{calGoal}")
        @Produces({"application/json"})
        public List<Report> findByCalGoal(@PathParam("calGoal") double calGoal) {
            Query query = em.createNamedQuery("Report.findByCalGoal");
            query.setParameter("calGoal",calGoal);
            return query.getResultList();
        }
        
         @GET
       @Path("findByUserId/{userId}")
        @Produces({"application/json"})
        public List<Report> findByUserId(@PathParam("userId") Integer userId) {
            TypedQuery<Report> q = em.createQuery("SELECT s FROM Report s WHERE s.userId.userId = :userId",Report.class);
            q.setParameter("userId", userId);
            return q.getResultList();
}
        
        @GET
        @Path("getCaloryConsumeBurnedRemain/{userId}/{reportDate}")
        @Produces({"application/json"})
        public JsonArray getCaloryConsumeBurnedRemain(@PathParam("userId") Integer userId, @PathParam("reportDate") String reportDate) {
            TypedQuery<Report> q = em.createQuery("SELECT s FROM Report s WHERE s.userId.userId = :userId AND s.reportDate = :reportDate",Report.class);
            q.setParameter("userId", userId);
            try {     
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = format.parse ( reportDate );  
            q.setParameter("reportDate",d);
        } catch (ParseException ex) {
            Logger.getLogger(UsrFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
            Report r = q.getSingleResult();
            double consume = r.getCalConsum();
            double burned = r.getCalBurned();
            double goal = r.getCalGoal();
            double remain = goal - (burned - consume);
 
            JsonObject consJs = Json.createObjectBuilder().add("caloryConsume",consume).build();
            JsonObject burnJs = Json.createObjectBuilder().add("caloryBurned",burned).build();
            JsonObject remJs = Json.createObjectBuilder().add("caloryGoalRemain",remain).build();
           JsonArrayBuilder arrB = Json.createArrayBuilder();
           JsonArray jArr = arrB.add(consJs).add(burnJs).add(remJs).build();
           return jArr;

        }
        
        @GET
        @Path("getPeriodCaloryBurnConsumeSteps/{userId}/{startDate}/{endDate}")
        @Produces({"application/json"})
        public JsonArray getPeriodCaloryBurnConsumeSteps (@PathParam ("userId") Integer userId, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
            
           TypedQuery<Report> q = em.createQuery("SELECT s FROM Report s WHERE s.userId.userId = :userId AND s.reportDate BETWEEN :startDate AND :endDate", Report.class) ;
           try {     
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date startD = format.parse ( startDate ); 
            Date endD = format.parse(endDate);
         
           q.setParameter("userId", userId);
           q.setParameter("startDate", startD);
           q.setParameter("endDate", endD);
        } catch (ParseException ex) {
            Logger.getLogger(UsrFacadeREST.class.getName()).log(Level.SEVERE, null, ex);}
          
           List<Report> reportList = q.getResultList();
           double burnSum = 0;
           double consumeSum = 0;
           int stepSum = 0;
           for(Report r: reportList) {
               burnSum += r.getCalBurned();
               consumeSum += r.getCalConsum();
               stepSum += r.getSteps();
           }
          
          JsonObject burnJs = Json.createObjectBuilder().add("totalCaloryBurn",burnSum ).build();
          JsonObject consJs = Json.createObjectBuilder().add("totalCaloryConsume",consumeSum ).build();
          JsonObject stepJs = Json.createObjectBuilder().add("totalSteps",stepSum ).build();
          JsonArrayBuilder arrB = Json.createArrayBuilder().add(burnJs).add(consJs).add(stepJs);
         JsonArray jArr = arrB.build();
         return jArr;
        }
}
