package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> { //Context
    private final Path directory;
    SerializeStrategy serializeStrategy;

    protected PathStorage(String dir) {//ok
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serializeStrategy = new Strategy();
    }

    @Override //+
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override //+
    protected List<Resume> getAll() {
        List<Resume> resumeList = new ArrayList<>(this.size());
        try (Stream<Path> paths = Files.list(directory)) {
            paths.forEach(path -> resumeList.add(doGet(path)));
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        return resumeList;
    }

    @Override //ok
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("File delete error", null);
        }
    }

    @Override //+
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid); //возвращаем полный путь
    }

    @Override //+
    protected boolean isExisting(Path path) {
        return path.toFile().exists();
    }

    @Override //+
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
            serializeStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path, path.toString(), e);
        }
    }

    @Override //+
    protected Resume doGet(Path path) {
        try {
            return serializeStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override //+
    protected void doUpdate(Path path, Resume resume) {
        try {
            serializeStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override //+
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File can not be deleted", path.toString(), new IOException("File exists but cannot be deleted"));
        }
    }
}
