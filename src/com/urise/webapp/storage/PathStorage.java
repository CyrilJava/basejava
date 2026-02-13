package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.SerializeStrategy;
import com.urise.webapp.storage.strategy.Strategy;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> { // Context
    private final Path directory;
    SerializeStrategy serializeStrategy;

    public PathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serializeStrategy = new Strategy();
    }

    @Override
    public int size() {
        try (Stream<Path> files = Files.list(directory)) { // try-with-resources
            return (int) files.count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    protected List<Resume> getAll() {
        try (Stream<Path> paths = Files.list(directory)) {
            return paths.map(this::doGet).collect(Collectors.toCollection(() -> new ArrayList<>(this.size())));
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> files = Files.list(directory)) {
            files.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("File delete error", null);
        }
    }

    @Override // возвращаем полный путь
    protected Path getSearchKey(String uuid) { return directory.resolve(uuid); }

    @Override
    protected boolean isExisting(Path path) {
        return path.toFile().exists();
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
            serializeStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path, path.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return serializeStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            serializeStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("File can not be deleted", path.toString(),
                    new IOException("File exists but cannot be deleted"));
        }
    }
}
