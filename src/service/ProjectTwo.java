package service;

import javax.ws.rs.core.*;
import javax.ws.rs.*;
import business.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import companydata.*;

import java.io.StringReader;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Path("CompanyServices")
public class ProjectTwo {

    @Context
    UriInfo uriInfo;

    @Path("Hello/{name}")
    @GET
    @Produces("application/json")
    public Response helloName(@PathParam("name") String name){
        return Response.ok("{\"hi\":\""+ name + "\"}").build();
    }


    @Path("department/")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateDepartment(Department department){
        BusinessLayer bl = new BusinessLayer();
        String json = "";
        Gson gson = new Gson();
        if(bl.validateDeptID(department.getCompany(),department.getId())){
            DataLayer dl = null;
            try {
                dl = new DataLayer(department.getCompany());
                Department updateDepartment = dl.updateDepartment(department);
                if(updateDepartment!=null){
                    json = "{\"success\":" + gson.toJson(updateDepartment)+ "}";
                }else{
                    json = "{\"error\":" +  "\"infomation could not be updated.}\"";
                }

            } catch (Exception e) {
                json = "{\"error\":" +  "\"infomation could not be updated.}\"";
            } finally {
                dl.close();
            }
        }else{
            json = "{\"error\":" +  "\"infomation could not be updated.}\"";
        }
        return Response.ok(json).build();
    }


    @Path("employee/")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateEmployee(String employeeCompanyJson) {
        BusinessLayer bl = new BusinessLayer();
        Gson gson = new Gson();
        String json = "";

        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(employeeCompanyJson);
        String companyName = jo.get("company").getAsString();
        int emp_id = jo.get("emp_id").getAsInt();


        if(bl.validateEmployeeID(companyName,emp_id)){
            DataLayer dl = null;
            try {
                dl = new DataLayer(companyName);
                Employee employee = dl.getEmployee(emp_id);
                if(jo.get("emp_name").getAsString() != null){
                    employee.setEmpName(jo.get("emp_name").getAsString());
                }

                if(jo.get("emp_no").getAsString() != null){
                    employee.setEmpNo(jo.get("emp_no").getAsString());
                }


                if(jo.get("job").getAsString() != null){
                    employee.setJob(jo.get("job").getAsString());
                }

                Double salary = -1.0;
                try{
                    salary = jo.get("salary").getAsDouble();
                    employee.setSalary(salary);
                }catch (NoSuchElementException nsee){

                }

                int dept = -1;
                try{
                    dept = jo.get("dept_id").getAsInt();
                    employee.setDeptId(dept);
                }catch (NoSuchElementException nsee){

                }

                int mng_id = -1;
                try{
                    mng_id = jo.get("mng_id").getAsInt();
                    employee.setDeptId(mng_id);
                }catch (NoSuchElementException nsee){

                }

                if(jo.get("hire_date").getAsString() != null){
                    Date hire_date = new SimpleDateFormat("yyyy-MM-dd").parse(jo.get("hire_date").getAsString());
                    java.sql.Date sql = new java.sql.Date(hire_date.getTime());
                    employee.setHireDate(sql);
                }

                employee = dl.updateEmployee(employee);
                if(employee!=null){
                    json = "{\"success\":" + gson.toJson(employee)+ "}";
                }else{
                    json = "{\"error\":" +  "\"infomation could not be updated.}\"";
                }

            } catch (Exception e) {
                json = "{\"error\":" +  "\"Also Here infomation could not be updated.}\"";
            } finally {
                dl.close();
            }
        }else{
            json = "{\"error\":" +  "\"infomation could not be updated.}\"";
        }
        return Response.ok(json).build();
    }


    @Path("timecard/")
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateTimeCard(String timeCardCompanyJson) {
        BusinessLayer bl = new BusinessLayer();
        Gson gson = new Gson();
        String json = "";

        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(timeCardCompanyJson);
        String companyName = jo.get("company").getAsString();
        int emp_id = jo.get("emp_id").getAsInt();
        int timecard_id = jo.get("timecard_id").getAsInt();


        if(bl.validateEmployeeID(companyName,emp_id)){
            DataLayer dl = null;
            try {
                dl = new DataLayer(companyName);
                Timecard timecard = dl.getTimecard(timecard_id);


                if(jo.get("start_time").getAsString() != null){
                    Timestamp start_time = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jo.get("start_time").getAsString()).getTime());
                    timecard.setStartTime(start_time);
                }

                if(jo.get("end_time").getAsString() != null){
                    Timestamp end_time = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jo.get("end_time").getAsString()).getTime());
                    timecard.setEndTime(end_time);
                }

                timecard = dl.updateTimecard(timecard);
                if(timecard!=null){
                    json = "{\"success\":" + gson.toJson(timecard)+ "}";
                }else{
                    json = "{\"error\":" +  "\"infomation could not be updated.}\"";
                }

            } catch (Exception e) {
                json = "{\"error\":" +  "\"Also Here infomation could not be updated.}\"";
            } finally {
                dl.close();
            }
        }else{
            json = "{\"error\":" +  "\"infomation could not be updated.}\"";
        }
        return Response.ok(json).build();
    }

    @Path("department/")
    @POST
    @Produces("application/json")
    public Response insertDepartment(@FormParam("company") String company, @FormParam("dept_name") String dept_name,
                                     @FormParam("dept_no") String dept_no, @FormParam("location") String location){
        BusinessLayer bl = new BusinessLayer();
        String json = "";
        Gson gson = new Gson();
        if(bl.isdeptNoUnique(company,dept_no)){
            DataLayer dl = null;
            try {
                dl = new DataLayer(company);
                Department department = new Department(company,dept_name,dept_name,location);
                Department department1 =  dl.insertDepartment(department);
                if(department1!=null){
                    json = "{\"success\":" + gson.toJson(department1)+ "}";
                }else{
                    json = "{\"error\":" +  "\"The provided infomation could not be updated.}\"";
                }
            } catch (Exception e) {
                json = "{\"error\":" +  "\"The provided infomation could not be updated.}\"";
            } finally {
                dl.close();
            }
        }else{
            json = "{\"error\":" +  "\"Please Provide an appropriate company name or unique department number.}\"";
        }
        return Response.ok(json).build();
    }


    @Path("employee/")
    @POST
    @Produces("application/json")
    public Response insertEmployee(@FormParam("company") String company, @FormParam("emp_name") String emp_name,
                                     @FormParam("emp_no") String emp_no, @FormParam("hire_date") String hire_date,
                                   @FormParam("job") String job, @FormParam("salary") Double salary,
                                   @FormParam("dept_id") int dept_id, @FormParam("mng_id") int mng_id){
        BusinessLayer bl = new BusinessLayer();
        String json = "";
        Gson gson = new Gson();

        if(bl.validateCompanyName(company)){
            if(bl.validateDeptID(company,dept_id)){
                if(bl.validateHireDate(hire_date)){
                    DataLayer dl = null;
                    try {
                        dl = new DataLayer(company);
                        Date hire_dates = new SimpleDateFormat("yyyy-MM-dd").parse(hire_date);
                        java.sql.Date sql = new java.sql.Date(hire_dates.getTime());
                        Employee employee = new Employee(emp_name,emp_no,sql,job,salary,dept_id,mng_id);
                        Employee employee1 =  dl.insertEmployee(employee);
                        if(employee1!=null){
                            json = "{\"success\":" + gson.toJson(employee1)+ "}";
                        }else{
                            json = "{\"error\":" +  "\"The provided infomation could not be updated.}\"";
                        }
                    } catch (Exception e) {
                        json = "{\"error\":" +  "\"The provided infomation could not be updated.}\"";
                    } finally {
                        dl.close();
                    }
                }else{
                    json = "{\"error\":" +  "\"Please provide valid HireDate.}\"";
                }
            }else{
                json = "{\"error\":" +  "\"Please provide valid department id.}\"";
            }
        }else{
            json = "{\"error\":" +  "\"Please provide valid company name.}\"";
        }
        return Response.ok(json).build();
    }


    @Path("timecard/")
    @POST
    @Produces("application/json")
    public Response insertTimeCard(@FormParam("company") String company, @FormParam("emp_id") int emp_id,
                                  @FormParam("start_time") String start_time, @FormParam("end_time") String end_time){
        BusinessLayer bl = new BusinessLayer();
        String json = "";
        Gson gson = new Gson();

        if(bl.validateCompanyName(company)){
            if(bl.validateEmployeeID(company,emp_id)){
                if(bl.validateTime(start_time) && bl.validateTime(end_time) && bl.compareTimeStamps(start_time,end_time)){
                    DataLayer dl = null;
                    try {
                        dl = new DataLayer(company);

                        Timestamp start = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start_time).getTime());
                        Timestamp end = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end_time).getTime());

                        Timecard timecard = new Timecard(start,end,emp_id);
                        Timecard timecard1 =  dl.insertTimecard(timecard);
                        if(timecard1!=null){
                            json = "{\"success\":" + gson.toJson(timecard1)+ "}";
                        }else{
                            json = "{\"error\":" +  "\"The provided infomation could not be updated.}\"";
                        }
                    } catch (Exception e) {
                        json = "{\"error\":" +  "\"The provided infomation could not be updated.}\"";
                    } finally {
                        dl.close();
                    }
                }else{
                    json = "{\"error\":" +  "\"Please provide valid Start Time and End Time.}\"";
                }
            }else{
                json = "{\"error\":" +  "\"Please provide valid Employee id.}\"";
            }
        }else{
            json = "{\"error\":" +  "\"Please provide valid company name.}\"";
        }
        return Response.ok(json).build();
    }


    @Path("company/")
    @DELETE
    @Produces("application/json")
    public Response deleteAllRecords(@QueryParam("company") String company) {

        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);

            int status = dl.deleteCompany(company);
            if(status>0){
                Gson gson = new Gson();
                json = "{\"success\":" + company+ "\"infomation deleted}\"";
            }else{
                json = "{\"error\":" + company+ "\"infomation could not be deleted.}\"";
            }

        } catch (Exception e) {
            json = "{\"error\":" + company+ "\"infomation could not be deleted.}\"";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }


    @Path("department/")
    @DELETE
    @Produces("application/json")
    public Response deleteDepartmentByID(@QueryParam("company") String company, @QueryParam("dept_id") int id) {

        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);

            int status = dl.deleteDepartment(company,id);
            if(status>0){
                Gson gson = new Gson();
                json = "{\"success\": Department" + id + "\"infomation deleted}\"";
            }else{
                json = "{\"error\": Department" + id+ "\"infomation could not be deleted.}\"";
            }

        } catch (Exception e) {
            json = "{\"error\": Department" + id+ "\"infomation could not be deleted.}\"";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }


    @Path("employee/")
    @DELETE
    @Produces("application/json")
    public Response deleteEmployeeByID(@QueryParam("company") String company, @QueryParam("emp_id") int id) {

        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);

            int status = dl.deleteEmployee(id);
            if(status>0){
                Gson gson = new Gson();
                json = "{\"success\": Department" + id + "\"infomation deleted}\"";
            }else{
                json = "{\"error\": Department" + id+ "\"infomation could not be deleted.}\"";
            }

        } catch (Exception e) {
            json = "{\"error\": Department" + id+ "\"infomation could not be deleted.}\"";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }


    @Path("timecard/")
    @DELETE
    @Produces("application/json")
    public Response deleteTimeCardByID(@QueryParam("company") String company, @QueryParam("timecard_id") int id) {

        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);

            int status = dl.deleteTimecard(id);
            if(status>0){
                Gson gson = new Gson();
                json = "{\"success\": TimeCard" + id + "\"infomation deleted}\"";
            }else{
                json = "{\"error\": TimeCard" + id+ "\"infomation could not be deleted.}\"";
            }

        } catch (Exception e) {
            json = "{\"error\": Department" + id+ "\"infomation could not be deleted.}\"";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }
















    @Path("department/")
    @GET
    @Produces("application/json")
    public Response getDepartmentByID(@QueryParam("company") String company,@QueryParam("dept_id") int id) {

        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);

            Department department = dl.getDepartment(company, id);
            if(department!=null){
                Gson gson = new Gson();
                json = gson.toJson(department);
            }else{
                json = "{'message':'The department with requested ID is not found.'}";
            }

        } catch (Exception e) {
            json = "{'message':'The department with requested ID is not \" +\n" +
                    "                    \"found.'}";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }

    @Path("departments/")
    @GET
    @Produces("application/json")
    public Response getAllDepartments(@QueryParam("company") String company){
        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);

            List<Department> departments = dl.getAllDepartment(company);
            if(departments!=null){
                Gson gson = new Gson();
                json = gson.toJson(departments);
            }else{
                json = "{'message':'The departments for the company name is not found.'}";
            }

        } catch (Exception e) {
            json = "{'message':'The departments for the company name is not found.'}";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }


    @Path("employee/")
    @GET
    @Produces("application/json")
    public Response getEmployeeByID(@QueryParam("company") String company,@QueryParam("emp_id") int emp_id){
        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);

            Employee employee = dl.getEmployee(emp_id);
            if(employee!=null){
                Gson gson = new Gson();
                json = gson.toJson(employee);
            }else{
                json = "{'message':'The Employee with the given emp_id does not exists in the system'}";
            }

        } catch (Exception e) {
            json = "{'message':'The Employee with the given emp_id does not exists in the system'}";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }

    @Path("employees/")
    @GET
    @Produces("application/json")
    public Response getAllEmployees(@QueryParam("company") String company){
        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);

            List<Employee> employee = dl.getAllEmployee(company);
            if(employee!=null){
                Gson gson = new Gson();
                json = gson.toJson(employee);
            }else{
                json = "{'message':'The Employee with the given emp_id does not exists in the system'}";
            }

        } catch (Exception e) {
            json = "{'message':'The Employee with the given emp_id does not exists in the system'}";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }

    @Path("timecard/")
    @GET
    @Produces("application/json")
    public Response getTimeCardByID(@QueryParam("company") String company,@QueryParam("timecard_id") int id){
        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);
            Timecard timecard = dl.getTimecard(id);
            if(timecard!=null){
                Gson gson = new Gson();
                json = gson.toJson(timecard);
            }else{
                json = "{'message':'The Timecard with the given timecard_id does not exists in the system'}";
            }

        } catch (Exception e) {
            json = "{'message':'The Timecard with the given timecard_id does not exists in the system'}";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }

    @Path("timecards/")
    @GET
    @Produces("application/json")
    public Response getTimeCardByEmployeeId(@QueryParam("company") String company,@QueryParam("emp_id") int id){
        DataLayer dl = null;
        String json = "";
        try {
            dl = new DataLayer(company);

            List<Timecard> timecard = dl.getAllTimecard(id);
            if(timecard!=null){
                Gson gson = new Gson();
                json = gson.toJson(timecard);
            }else{
                json = "{'message':'The Employee with the given emp_id does not exists in the system'}";
            }

        } catch (Exception e) {
            json = "{'message':'The Employee with the given emp_id does not exists in the system'}";
        } finally {
            dl.close();
        }

        return Response.ok(json).build();
    }


}

