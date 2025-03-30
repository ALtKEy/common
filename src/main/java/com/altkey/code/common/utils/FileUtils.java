package com.altkey.code.common.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * FileUtils
 * 파일 조작 유틸 클래스
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-26
 */
public class FileUtils {
    //
    private FileUtils() {}

    /**
     * 파일이 있는지 확인
     *
     * @param src
     * @return
     * @author Kim Jung-tae(altkey)
     */
    public static boolean isExists(final String src) {
        return Files.exists(Path.of(src));
    }

    /**
     * 파일이 없는지 확인
     *
     * @param src
     * @return
     * @author Kim Jung-tae(altkey)
     */
    public static boolean isNotExists(final String src) {
        return Files.notExists(Path.of(src));
    }

    /**
     * 파일이 있으면 삭제
     *
     * @param src
     * @return
     * @throws IOException
     * @author Kim Jung-tae(altkey)
     */
    public static boolean deleteIfExists(final String src) throws IOException {
        try {
            return Files.deleteIfExists(Path.of(src));
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    /**
     * 폴더내 파일을 검사하여 삭제
     *
     * @param src
     * @throws IOException
     * @author Kim Jung-tae(altkey)
     */
    public static void deleteDirectoryIfExists(final String src) throws IOException {
        final Path srcPath = Paths.get(src);
        try (Stream<Path> stream = Files.walk(srcPath)) {
            stream.sorted(Comparator.reverseOrder())
                    //.map(Path::toFile)
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            Files.deleteIfExists(srcPath);
        }
    }

    /**
     * 폴더가 없으면 생성
     *
     * @param src
     * @return path
     * @throws IOException
     * @author Kim Jung-tae(altkey)
     */
    public static String createDirectoryIfNotExists(final String src) throws IOException {
        try {
            return Files.createDirectories(Path.of(src)).toString();
        } catch (FileAlreadyExistsException e) {
            return src;
        }
    }

    /**
     * 임시폴더가 없으면 생성
     *
     * @param src
     * @param prefix
     * @return path
     * @throws IOException
     * @author Kim Jung-tae(altkey)
     */
    public static String createTempDirectoryIfNotExists(final String src, final String prefix) throws IOException {
        try {
            return Files.createTempDirectory(Path.of(src), prefix).toString();
        } catch (FileAlreadyExistsException e) {
            return src;
        }
    }
}
