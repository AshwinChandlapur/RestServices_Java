import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import companydata.DataLayer;
import companydata.Department;
import companydata.Employee;
import service.*;

import javax.ws.rs.core.Response;
import java.util.List;

public class MyTester {


    public static void main(String[] args) {
        ProjectTwo p2 = new ProjectTwo();
//        System.out.println(p2.helloName("Bryan").getEntity());
//
//        System.out.println(p2.getDepartmentByID("ay6582",2).getEntity());
//        System.out.println(p2.getAllDepartments("ay6582").getEntity());
//        System.out.println(p2.getEmployeeByID("ay6582",1).getEntity());
        System.out.println(p2.getAllEmployees("ay6582").getEntity());
//        System.out.println(p2.getTimeCardByID("ay6582",1).getEntity());
//        System.out.println(p2.getTimeCardByEmployeeId("ay6582",1).getEntity());
//
//        Department department = new Department(2,"ay6582","IT","d11","Rochester");
//        System.out.println(p2.updateDepartment(department).getEntity());


        String employee = "{\"company\":\"ay6582\"," +
                "\"emp_id\":1," +
                "\"emp_name\":\"French\"," +
                "\"emp_no\":\"rituserid-e1b\"," +
                "\"hire_date\":\"2018-06-16\"," +
                "\"job\":\"Programmer\"," +
                "\"salary\":6000," +
                "\"dept_id\":1," +
                "\"mng_id\":2" +
                "}";

        System.out.println(p2.updateEmployee(employee).getEntity());




    }
}
//        System.out.println(p2.deleteAllRecords("ay6582").getEntity());