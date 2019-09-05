package question2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class JPanelListe2 extends JPanel implements ActionListener, ItemListener {

	private Stack<List<String>> lists = new Stack<List<String>>();
	
    private JPanel cmd = new JPanel();
    private JLabel afficheur = new JLabel();
    private JTextField saisie = new JTextField();

    private JPanel panelBoutons = new JPanel();
    private JButton boutonRechercher = new JButton("rechercher");
    private JButton boutonRetirer = new JButton("retirer");

    private CheckboxGroup mode = new CheckboxGroup();
    private Checkbox ordreCroissant = new Checkbox("croissant", mode, false);
    private Checkbox ordreDecroissant = new Checkbox("décroissant", mode, false);

    private JButton boutonOccurrences = new JButton("occurrence");

    private JButton boutonAnnuler = new JButton("annuler");

    private TextArea texte = new TextArea();

    private List<String> liste;
    private Map<String, Integer> occurrences;

    public JPanelListe2(List<String> liste, Map<String, Integer> occurrences) {
        this.liste = liste;
        this.occurrences = occurrences;

        cmd.setLayout(new GridLayout(3, 1));

        cmd.add(afficheur);
        cmd.add(saisie);

        panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBoutons.add(boutonRechercher);
        panelBoutons.add(boutonRetirer);
        panelBoutons.add(new JLabel("tri du texte :"));
        panelBoutons.add(ordreCroissant);
        panelBoutons.add(ordreDecroissant);
        panelBoutons.add(boutonOccurrences);
        panelBoutons.add(boutonAnnuler);
        cmd.add(panelBoutons);


        if(liste!=null && occurrences!=null){
            afficheur.setText(liste.getClass().getName() + " et "+ occurrences.getClass().getName());
            texte.setText(liste.toString());
        }else{
            texte.setText("la classe Chapitre2CoreJava semble incomplète");
        }

        setLayout(new BorderLayout());

        add(cmd, "North");
        add(texte, "Center");

        boutonRechercher.addActionListener(this);
        boutonOccurrences.addActionListener(this);
		boutonRetirer.addActionListener(this);
		ordreCroissant.addItemListener(this);
		ordreDecroissant.addItemListener(this);
		boutonAnnuler.addActionListener(this);

    }

    public void actionPerformed(ActionEvent ae) {
        try {
            boolean res = false;
            if (ae.getSource() == boutonRechercher || ae.getSource() == saisie) {
                res = liste.contains(saisie.getText());
                Integer occur = occurrences.get(saisie.getText());
                afficheur.setText("résultat de la recherche de : "
                    + saisie.getText() + " -->  " + res);
            } else if (ae.getSource() == boutonRetirer) {
                res = retirerDeLaListeTousLesElementsCommencantPar(saisie
                    .getText());
                afficheur
                .setText("résultat du retrait de tous les éléments commençant par -->  "
                    + saisie.getText() + " : " + res);
            } else if (ae.getSource() == boutonOccurrences) {
                Integer occur = occurrences.get(saisie.getText());
                if (occur != null)
                    afficheur.setText(" -->  " + occur + " occurrence(s)");
                else
                    afficheur.setText(" -->  ??? ");
            }
			else if(ae.getSource()==boutonAnnuler){
				if(lists.isEmpty())
					return;
				List<String> s = lists.pop();
				liste=s;
				texte.setText(liste.toString());
			}
            texte.setText(liste.toString());

        } catch (Exception e) {
            afficheur.setText(e.toString());
        }
    }

    public void itemStateChanged(ItemEvent ie) {
        List<String> s = new LinkedList<String>();
		s.addAll(liste);
		lists.push(s);
		
		if (ie.getSource() == ordreCroissant){
			Collections.sort(liste);
			texte.setText(liste.toString());
		}
        else if (ie.getSource() == ordreDecroissant){
			Collections.sort(liste,new Comparateur());
			texte.setText(liste.toString());
		}
		

        texte.setText(liste.toString());
    }

    private class Comparateur implements Comparator<String>{
		public int compare(String t1,String t2){
			return t2.compareTo(t1);
		}
	}

    private boolean retirerDeLaListeTousLesElementsCommencantPar(String prefixe) {
        
		boolean resultat = false;
        Iterator<String> it = liste.iterator();
		while(it.hasNext())
		{
			String s= it.next();

			if(s.startsWith(prefixe)){
				List<String> ls = new LinkedList<String>();
				ls.addAll(liste);
				lists.push(ls);
				it.remove();
				resultat=true;

				occurrences.remove(s);
				occurrences.put(s,0);
			}
        }
        return resultat;
    }

}