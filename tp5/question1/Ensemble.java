package question1;

import java.util.*;

public class Ensemble<T> extends AbstractSet<T> {

	protected java.util.Vector<T> table = new java.util.Vector<T>();

	public int size() {
		return table.size();
	}

	public Iterator<T> iterator() {
		return table.iterator();
	}

	public boolean add(T t) {
	
	if(this.table.contains(t))
	{ return false;} 
    else { this.table.add(t);
		   return true;
			} 
    }

	public Ensemble<T> union(Ensemble<? extends T> e) {
		
	 Ensemble<T> un= new Ensemble<T>();
     un.addAll(this);
     un.addAll(e);
     return un;

	}

	public Ensemble<T> inter(Ensemble<? extends T> e) {
	 Ensemble<T> in= new Ensemble<T>();
     in.addAll(this);
     in.retainAll(e);
     return in;

	}

	public Ensemble<T> diff(Ensemble<? extends T> e) {
	 Ensemble<T> di= new Ensemble<T>();
     di.addAll(this);
     di.removeAll(e);
     return di;	
	}

	Ensemble<T> diffSym(Ensemble<? extends T> e) {
	 Ensemble<T> dis = new Ensemble<T>();
     Ensemble<T> dis1 = union(e);
     Ensemble<T> dis2 = inter(e);
     dis.addAll(dis1);
     dis.removeAll(dis2);
        
     return dis;

	}
	
}