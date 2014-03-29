package others;


import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cristian
 */
public class TreeView {
    List<String> titles_;
    String icon_;

    public TreeView(List<String> titles_, String icon_) {
        this.titles_ = titles_;
        this.icon_ = icon_;
    }
    
    @Override
    public String toString() {
        int size = titles_.size();
        String data = "<li" + (size==0? " class=\"active\"" : "")  + "><a href=\"Index\"><i class=\"fa " + this.icon_  + "\"></i> Dashboard</a></li>";
        for(int i = 0; i < size; i++) {
            data += "<li";
            if(i + 1 == size) { // El último lo ponemos activo
                data += " class=\"active\"";
            }
            data += ">" + titles_.get(i) + "</li>";
        }
        
        return data;
    }
    
    public String toStringTitle() {
        int size = titles_.size();
        String data = "";
        for (int i = 0; i < size; i++) {
            data += titles_.get(i);
            if(i +1 != size) {
                data += " | ";
            }
        }
        return data;
    }
}
