package it.unimol.decathlon.UI;

import it.unimol.decathlon.app.manager.ManagerGeneralClassific;

public class FinalScreen {

   public  FinalScreen () {

   }

   public void start(){
       this.printFinalClassific();

       this.printWinner();
   }

   private void printFinalClassific() {
       System.out.println("######CLASSIFICA FINALE######");
       System.out.println("");
       ManagerGeneralClassific.getClassific().forEach(name->{

           System.out.println(name);});
   }

   private void printWinner() {
       System.out.print("IL VINCITORE E' ");
       System.out.println(ManagerGeneralClassific.getWinner());
   }
}
