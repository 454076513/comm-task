package self.mf.delay;

public interface ExecuteListener<K,V>{
        void toDo(K key,V param);
}