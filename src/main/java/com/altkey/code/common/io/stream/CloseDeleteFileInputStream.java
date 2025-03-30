package com.altkey.code.common.io.stream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * FileInputStream을 상속받아 close() 시 파일을 삭제하는 스트림 클래스
 * 주로 파일 전송후 삭제를 위해 사용
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public class CloseDeleteFileInputStream extends FileInputStream {
    //
    private final File file;

    public CloseDeleteFileInputStream(File file) throws FileNotFoundException {
        super(file);
        this.file = file;
    }

    public CloseDeleteFileInputStream(String file) throws FileNotFoundException {
        super(file);
        this.file = new File(file);
    }

    @Override
    public void close() throws IOException {
        super.close();
        Files.delete(Path.of(file.getCanonicalPath()));
    }
}
