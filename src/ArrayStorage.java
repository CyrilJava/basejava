/**
 * Array based storage for Resumes
 */

//Реализуйте методы save, get, delete, clear, getAll, size в классе ArrayStorage, организовав хранение резюме в массиве
//Храните все резюме в начале storage (без пустот в виде null), чтобы не перебирать каждый раз все 10_000 элементов
//При реализации метода delete учитывайте, что после удаления резюме между оставшимися резюме не должно быть пустых ячеек, заполненных null
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int arraySize = 0;

    void clear() {
        for (int i = 0; i < arraySize; i++) {
            storage[i] = null;
        }
        arraySize = 0;
    }

    void save(Resume r) {
        boolean newID = true;
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                System.out.println("Uuid " + r.uuid + " already exists");
                newID = false;
            }
        }
        if (newID) {
            storage[arraySize] = r;
            arraySize++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < arraySize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (arraySize > 0) {
            int delNum = -1;
            for (int i = 0; i < arraySize; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    delNum = i;
                }
            }
            if (delNum >= 0) {
                /*for (int i = 0; i < arraySize; i++) {
                    if (i >= delNum && i != (arraySize - 1))
                        storage[i] = storage[i + 1];
                }*/
                storage[delNum] = storage[arraySize - 1];
                storage[arraySize - 1] = null;
                arraySize--;
            } else {
                System.out.println("Uuid " + uuid + " not found");
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] onlyResumes = new Resume[arraySize];
        if (arraySize > 0) {
            for (int i = 0; i < arraySize; i++) {
                onlyResumes[i] = storage[i];
            }
        }
        return onlyResumes;
    }

    int size() {
        return arraySize;
    }
}
