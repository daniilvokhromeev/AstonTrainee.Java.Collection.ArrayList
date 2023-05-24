package com.main.arraylist.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
/**
 * Реализация обобщенного списка на основе массива. Элементы списка должны реализовывать интерфейс Comparable.
 * Этот класс предоставляет методы для операций с элементами, такими как добавление, получение, удаление и т.д.
 * <p>
 * Класс имеет несколько конструкторов для создания списка с различными начальными параметрами.
 * <p>
 * Класс реализует интерфейс CustomList.
 *
 * @param <T> тип элементов в этом списке
 */
public class CustomArrayList<T extends Comparable<T>> implements CustomList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size = 0;

    /**
     * Конструктор без параметров создает список с начальной емкостью 10.
     */
    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Конструктор создает список с указанной начальной емкостью.
     *
     * @param capacity начальная емкость списка
     * @throws IllegalArgumentException если указанная емкость меньше 0
     */
    public CustomArrayList(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Invalid capacity: " + capacity);
        elements = new Object[capacity];
    }

    /**
     * Конструктор создает список и инициализирует его элементами из указанной коллекции.
     *
     * @param collection коллекция элементов, которые будут помещены в список
     * @throws NullPointerException если указанная коллекция равна null
     */
    public CustomArrayList(Collection<? extends T> collection) {
        if (collection == null) throw new NullPointerException("The input collection is null");
        elements = collection.toArray();
        size = elements.length;
    }

    /**
     * Добавляет указанный элемент в конец списка.
     *
     * @param element элемент, который нужно добавить в список
     */
    @Override
    public void add(T element) {
        if (size == elements.length) {
            ensureCapacity();
        }
        elements[size++] = element;
    }

    /**
     * Возвращает элемент на указанной позиции в этом списке.
     *
     * @param index индекс элемента, который нужно вернуть
     * @return элемент на указанной позиции
     * @throws IndexOutOfBoundsException если индекс находится вне диапазона (index < 0 || index >= size())
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + size);
        }
        return (T) elements[index];
    }

    /**
     * Вставляет указанный элемент на указанную позицию в этом списке.
     *
     * @param index   индекс, по которому нужно вставить указанный элемент
     * @param element элемент, который нужно вставить
     * @throws IndexOutOfBoundsException если индекс находится вне диапазона (index < 0 || index > size())
     */
    @Override
    public void insert(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + size);
        }
        if (size == elements.length) {
            ensureCapacity();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Удаляет элемент на указанной позиции в этом списке.
     *
     * @param index индекс элемента, который нужно удалить
     * @return удаленный элемент
     * @throws IndexOutOfBoundsException если индекс находится вне диапазона (index < 0 || index >= size())
     */
    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + size);
        }
        T removedElement = (T) elements[index];
        if (size - 1 - index >= 0) System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
        size--;
        return removedElement;
    }

    /**
     * Возвращает количество элементов в этом списке.
     *
     * @return количество элементов в этом списке
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Удаляет все элементы из этого списка.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    private void ensureCapacity() {
        int newIncreasedCapacity = elements.length * 2;
        elements = Arrays.copyOf(elements, newIncreasedCapacity);
    }

    /**
     * Сортирует элементы этого списка в порядке возрастания.
     * Используется быстрая сортировка.
     */
    @Override
    public void sort() {
        sort(SortMethod.QUICK_SORT, null);
    }

    /**
     * Сортирует элементы этого списка в порядке, определенном указанным компаратором.
     * Используется быстрая сортировка.
     *
     * @param comparator компаратор, определяющий порядок элементов
     */
    @Override
    public void sort(Comparator<? super T> comparator) {
        sort(SortMethod.QUICK_SORT, comparator);
    }

    /**
     * Сортирует элементы этого списка в порядке, определенном указанным компаратором и методом сортировки.
     *
     * @param sortMethod метод сортировки, который следует использовать
     * @param comparator компаратор, определяющий порядок элементов
     * @throws IllegalArgumentException если указан неподдерживаемый метод сортировки
     */
    @Override
    public void sort(SortMethod sortMethod, Comparator<? super T> comparator) {
        switch (sortMethod) {
            case QUICK_SORT -> quickSort(0, size - 1, comparator);
            case MERGE_SORT -> mergeSort(0, size - 1, comparator);
            default -> throw new IllegalArgumentException("Unsupported sorting method: " + sortMethod);
        }
    }

    private void quickSort(int low, int high, Comparator<? super T> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, comparator);
            quickSort(low, pivotIndex - 1, comparator);
            quickSort(pivotIndex + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<? super T> comparator) {
        T pivot = (T) elements[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare((T) elements[j], pivot, comparator) <= 0) {
                i++;

                T temp = (T) elements[i];
                elements[i] = elements[j];
                elements[j] = temp;
            }
        }

        T temp = (T) elements[i + 1];
        elements[i + 1] = elements[high];
        elements[high] = temp;

        return i + 1;
    }

    private void mergeSort(int low, int high, Comparator<? super T> comparator) {
        if (high <= low) return;
        int mid = low + (high - low) / 2;
        mergeSort(low, mid, comparator);
        mergeSort(mid + 1, high, comparator);
        merge(low, mid, high, comparator);
    }

    private void merge(int low, int mid, int high, Comparator<? super T> comparator) {
        Object[] temp = new Object[high - low + 1];
        int i = low, j = mid + 1, k = 0;

        while (i <= mid && j <= high) {
            if (compare((T) elements[i], (T) elements[j], comparator) <= 0) {
                temp[k++] = elements[i++];
            } else {
                temp[k++] = elements[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = elements[i++];
        }

        while (j <= high) {
            temp[k++] = elements[j++];
        }

        System.arraycopy(temp, 0, elements, low, temp.length);
    }


    private int compare(T o1, T o2, Comparator<? super T> comparator) {
        return comparator == null ? o1.compareTo(o2) : comparator.compare(o1, o2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomArrayList<?> that = (CustomArrayList<?>) o;
        return size == that.size && Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(elements);
        result = 31 * result + size;
        return result;
    }

    @Override
    public String toString() {
        return "CustomArrayList{" +
                "elements=" + Arrays.toString(elements) +
                ", size=" + size +
                '}';
    }
}