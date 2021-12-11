package com.evan.firstspring.view;

import com.evan.firstspring.bean.Customer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CustomerView {
    private int index;
    private String id;
    private String name;
    private String tag;

    public static List<CustomerView> getCustomerView(List<Customer> customerList)
    {
        List<CustomerView> customerViewList=new CopyOnWriteArrayList<>();
        for(int i=0;i<customerList.size();i++)
        {
            Customer customer=customerList.get(i);
            String title;
            if(!customer.getCustomerTag())
            {
                title="零售客户";
            }else
            {
                title="批发客户";
            }

            customerViewList.add(new CustomerView(i,customer.getCustomerId(),customer.getCustomerName(),title));
        }
        return customerViewList;
    }

    public CustomerView(int index, String id, String name, String tag) {
        this.index = index;
        this.id = id;
        this.name = name;
        this.tag = tag;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
