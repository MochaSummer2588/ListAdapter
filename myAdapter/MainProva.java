package myAdapter;

public class MainProva 
{
    public static void main(String[] args) 
    {
        // Creazione di un ListAdapter vuoto
        ListAdapter list = new ListAdapter();
        
        // Aggiunta di elementi alla lista
        list.add(0, "Primo");
        list.add(1, "Secondo");
        list.add(2, "Terzo");
        
        // Stampa degli elementi della lista
        for (int i = 0; i < list.size(); i++) 
        {
            System.out.println("Elemento " + i + ": " + list.get(i));
        }
        
        // Verifica dell'indice di un elemento
        int index = list.indexOf("Secondo");
        System.out.println("Indice di 'Secondo': " + index);
    }
}
