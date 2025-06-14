package myAdapter;

public class MainProva 
{
    public static void main(String[] args) 
    {
        // Creazione di un ListAdapter vuoto
        ListAdapter list = new ListAdapter();
        
        // Aggiunta di elementi alla lista
        list.add(5, "Primo");
        
        // Stampa degli elementi della lista
        
        for (int i = 0; i < list.size(); i++) 
        {
            System.out.println("Elemento " + i + ": " + list.get(i));
        }
    }
}
