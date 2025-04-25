package SelectionSort;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomArrayList<T> implements Iterable{
    private static final int DEFAULT_CAPACITY = 10; // Начальная емкость массива
    private T[] elements; // Массив для хранения элементов
    private int size; // Текущее количество элементов в списке

    // Конструктор по умолчанию
    public CustomArrayList() {
        this.elements = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Конструктор с указанием начальной емкости
    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        this.elements = (T[]) new Object[initialCapacity];
        this.size = 0;
    }

    // Добавление элемента в конец списка
    public void add(T element) {
        ensureCapacity(size + 1); // Убедимся, что массив достаточно большой
        elements[size++] = element;
    }

    // Получение элемента по индексу
    public T get(int index) {
        checkIndex(index); // Проверка корректности индекса
        return (T) elements[index];
    }

    // Установка значения элемента по индексу
    public void set(int index, T element) {
        checkIndex(index); // Проверка корректности индекса
        elements[index] = element; // Устанавливаем новое значение
    }

    // Удаление элемента по индексу
    public T remove(int index) {
        checkIndex(index); // Проверка корректности индекса
        T removedElement = (T) elements[index]; //Удаляем эл и сохраняем его
        // Сдвигаем элементы влево, чтобы заполнить пустоту
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; // Очищаем последний элемент и уменьшаем размер
        return removedElement;
    }

    // Получение текущего размера списка
    public int size() {
        return size;
    }

    // Проверка, пуст ли список
    public boolean isEmpty() {
        return size == 0;
    }

    // Увеличение емкости массива, если необходимо
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length * 2; // Увеличиваем емкость в 2 раза
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    // Проверка корректности индекса
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс: " + index + ", Размер: " + size);
        }
    }

    // Переопределение toString для удобного вывода
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Переопределение equals и hashCode для корректного сравнения списков
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomArrayList<?> that = (CustomArrayList<?>) o;
        return size == that.size && Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }

    @Override
    //Метод iterator() — часть интерфейса Iterable<T>,
    // который нужно реализовать, чтобы CustomArrayList<T> можно было перебирать в for-each цикле.
    public Iterator<T> iterator() {
        //Возвращает новый экземпляр CustomIterator, который будет управлять перебором элементов.
        return new CustomIterator();
    }

    //CustomIterator — внутренний класс, реализующий интерфейс Iterator<T>.
// Он должен реализовать методы hasNext(), next(), remove().
    private class CustomIterator implements Iterator<T> {
        //Запоминает текущий индекс при обходе списка (изначально 0)
        private int currentIndex = 0;

        //hasNext Проверяет, есть ли ещё элементы в списке.
        //Возвращает true, если currentIndex меньше size (размера списка).
        //Иначе возвращает false, означая, что элементы закончились.
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        //Возвращает текущий элемент массива elements[currentIndex].
        //Увеличивает currentIndex (++), чтобы перейти к следующему элементу.
        //Если элементов больше нет (!hasNext()), бросает исключение NoSuchElementException.
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the collection");
            }
            return elements[currentIndex++];
        }

        //Удаление элементов не поддерживается → бросается UnsupportedOperationException.
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Удаление не поддерживается");
        }
    }
}
