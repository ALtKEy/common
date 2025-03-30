package com.altkey.code.common.io.stream;

import com.altkey.code.common.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * FileInputStream을 상속받아 close() 시 파일이 위치한 디렉토리를 삭제하는 스트림 클래스
 * 주로 파일 전송후 삭제를 위해 사용
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public class CloseDeleteDirectoryFileInputStream extends FileInputStream {
    //
    private final File file;

    public CloseDeleteDirectoryFileInputStream(File file) throws FileNotFoundException {
        super(file);
        this.file = file;
    }

    public CloseDeleteDirectoryFileInputStream(String file) throws FileNotFoundException {
        super(file);
        this.file = new File(file);
    }

    @Override
    public void close() throws IOException {
        super.close();
        FileUtils.deleteDirectoryIfExists(file.getCanonicalPath().replace(file.getName(), ""));
    }
}