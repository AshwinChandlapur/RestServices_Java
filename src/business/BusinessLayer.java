package business;


import com.google.gson.Gson;
import companydata.DataLayer;
import companydata.Department;
import companydata.Employee;

import javax.ws.rs.core.Response;

public class BusinessLayer {


    public boolean validateDeptID(String company,int dept_id){
        DataLayer dl = null;
        try {
            dl = new DataLayer(company);

            Department department = dl.getDepartment(company,dept_id);
            if(department!=null){
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            return false;
        } finally {
            dl.close();
        }
    }


    public boolean validateEmployeeID(String company,int emp_id){
        DataLayer dl = null;
        try {
            dl = new DataLayer(company);

            Employee employee = dl.getEmployee(emp_id);
            if(employee!=null){
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            return false;
        } finally {
            dl.close();
        }
    }

}