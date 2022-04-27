public interface IEntryTokenizer<U,V> {
    U[] tokenize(U entry);
    U[] tokenize(U entry, V seperator);
}
