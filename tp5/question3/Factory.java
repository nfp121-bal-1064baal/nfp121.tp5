package question3;

import java.util.Set;

public interface Factory<T> {
    public Set<T> create();
}