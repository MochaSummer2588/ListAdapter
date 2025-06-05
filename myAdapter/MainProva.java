package myAdapter;

public class MainProva 
{
    public static void main(String[] args) 
    {
        // Creazione di un ListAdapter vuoto
        ListAdapter list = new ListAdapter();
        ListAdapter list2 = new ListAdapter(10, 5);
        
        // Aggiunta di elementi alla lista
        list.add(0, "Primo");
        list.add(1, "Secondo");
        list.add(2, "Terzo");
        list.add("HELL NAH MAN!");

        //list2.add(0, "Primo");
        System.out.println("Provo ad aggiungere un elemento alla posizione -1 list2!");
        list2.add(0, 90);
        list2.add(0, 56);
        
        // Stampa degli elementi della lista
        
        for (int i = 0; i < list.size(); i++) 
        {
            System.out.println("Elemento " + i + ": " + list.get(i));
        }
        

        for (int i = 0; i < list2.size(); i++) 
        {
            System.out.println("Elemento lista2: " + i + ": " + list2.get(i));
        }

        System.out.println("PROVO IL METODO ADDALL SULLA LISTA2 DELLA LISTA 1, QUINDI SULLA LISTA 2 CI SARANNO TUTTI GLI ELEMENTI DELLA LISTA 1!");
        list2.addAll(list);
        for (int i = 0; i < list2.size(); i++) 
        {
            System.out.println("Elemento lista2 DOPO ADD ALL: " + i + ": " + list2.get(i));
        }

    }
}
