package business;


import com.google.gson.Gson;
import companydata.DataLayer;
import companydata.Department;
import companydata.Employee;
import companydata.Timecard;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BusinessLayer {


    public boolean isEmployeeNoUnique(String company,String emp_no){
        DataLayer dl = null;
        try {
            dl = new DataLayer(company);

            List<Employee> employees = dl.getAllEmployee(company);
            for(int i=0;i<employees.size();i++){
                if(emp_no.equals(employees.get(i).getEmpNo())){
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            return false;
        } finally {
            dl.close();
        }
    }

    public boolean isdeptNoUnique(String company,String dept_no){
        DataLayer dl = null;
        try {
            dl = new DataLayer(company);

            List<Department> departments = dl.getAllDepartment(company);
            for(int i=0;i<departments.size();i++){
                if(dept_no.equals(departments.get(i).getDeptNo())){
                    return false;
                }
            }
            return true;

        } catch (Exception e) {
            return false;
        } finally {
            dl.close();
        }
    }

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

    public boolean validateTimeCardID(String company,int timecard_id){
        DataLayer dl = null;
        try {
            dl = new DataLayer(company);

            Timecard timecard = dl.getTimecard(timecard_id);
            if(timecard!=null){
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

    public boolean validateCompanyName(String company){
        if(company.equals("ay6582")){
            return true;
        }else{
            return false;
        }
    }

    public boolean validateHireDate(String date){
        try{
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            Date current_date = new SimpleDateFormat("yyyy-MM-dd").parse(timeStamp);
            Date hire_date = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            if(hire_date.compareTo(current_date)<=0){
                Calendar c = Calendar.getInstance();
                c.setTime(hire_date);
                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                if(dayOfWeek!=1 && dayOfWeek!=7){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean validateTime(String timestamp){
        try{
            Date current_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);

            Calendar c = Calendar.getInstance();
            c.setTime(current_date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek!=1 && dayOfWeek!=7){
                int hour = c.get(Calendar.HOUR_OF_DAY);
                if(hour>=8 && hour<18){
                    System.out.println(hour);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean compareTimeStamps(String timestamp_start,String timestamp_end){
        try{
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

            Date start = format.parse(timestamp_start);
            Date end = format.parse(timestamp_end);
            long diff = end.getTime() - start.getTime();
            long diffHours = diff / (60 * 60 * 1000) % 24;

            if(diffHours<1){
                System.out.println(diffHours);
                return false;
            }else{
                System.out.println(diffHours);
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }


}