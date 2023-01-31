package point;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import order.OrderDAO;
import order.OrderVO;
import total.TotalDAO;

public class Point_1 {

   

   public void po() throws ClassNotFoundException, SQLException {
      
      OrderDAO od = new OrderDAO();
      PointDAO da = new PointDAO();
      TotalDAO to = new TotalDAO();
      
      int number;
      
      while (true) {
         int p = JOptionPane.showConfirmDialog(null, "--------포인트---------\n"
               + "포인트를 적립하시겠습니까?\n", "포인트창", JOptionPane.YES_NO_OPTION);
         if(p == JOptionPane.YES_OPTION) {
            int num = Integer.parseInt(JOptionPane.showInputDialog("회원번호 입력"));
         
            
            da.Select(num);    // 회원번호 있는지 없는지 확인 메소드
            if(num == da.getNumber()) {   // 테이블내의 번호를 받아서 구별          
               boolean p2 = da.UPDATE1(num);
               if (p2) {
                  
                  int point = da.getResult();
                  JOptionPane.showMessageDialog(null, "포인트 적립 완료\n현재 포인트 : " + point);
                  
                  ArrayList<OrderVO> vo = od.getAllInfo();
                  
                  for(OrderVO vv : vo) {
                     to.Select(vv.getProductName());
                     if(!vv.getProductName().equals(to.getMenu())) {
                        to.Insert(vv.getProductName(), vv.getChoice());
                     }
                     else {
                        to.UPdate(vv.getProductName(), vv.getChoice());
                     }
                  }
                  
                  if(point > 4)  // 포인트 5달성시 초기화 
                  {
                     JOptionPane.showMessageDialog(null, "포인트 5점 달성으로\n아메리카노 무료 쿠폰을 지급합니다.");
                     da.UPDATE2(num);
                     JOptionPane.showMessageDialog(null, "포인트가 초기화됩니다.\n잔액 포인트: " + da.getResult());
                  }
                  
                  od.ODelete(); // 테이블 초기화메소드
                  break; // 초기메뉴로 되돌리는 용도 
               }
               else 
                  JOptionPane.showMessageDialog(null, "포인트 적립 오류");
               
            }
            else 
               JOptionPane.showMessageDialog(null, "가입된 번호가 아닙니다.");
            //  회원번호가 등록이 안되있을때 출력 
            
         }
         else if(p == JOptionPane.NO_OPTION) {
            
            JOptionPane.showMessageDialog(null, "결제 완료");
            
            ArrayList<OrderVO> vo = od.getAllInfo();
            
            for(OrderVO vv : vo) {
               to.Select(vv.getProductName());
               if(!vv.getProductName().equals(to.getMenu())) {
                  to.Insert(vv.getProductName(), vv.getChoice());
               }
               else {
               to.UPdate(vv.getProductName(), vv.getChoice());
               }
            }
            od.ODelete();  // 테이블 초기화메소드 
            break; // 초기메뉴로 되돌리는 용도
         }
         
      }   
      
   }         

}