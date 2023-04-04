package controllers;

import ninja.Context;
import ninja.Result;
import ninja.Results;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;

import java.io.InputStream;

/**
 * @className: UploadController
 * @description:
 * @author: Aim
 * @date: 2023/4/3
 **/
public class UploadController {

    public Result uploadFinish(Context context) throws Exception {
        // Make sure the context really is a multipart context...
        if (context.isMultipart()) {
            // This is the iterator we can use to iterate over the contents of the request.
            FileItemIterator fileItemIterator = context.getFileItemIterator();
            while (fileItemIterator.hasNext()) {
                FileItemStream item = fileItemIterator.next();
                String name = item.getFieldName();
                InputStream stream = item.openStream();
                String contentType = item.getContentType();
                if (item.isFormField()) {
                    // do something with the form field
                } else {
                    // process file as input stream
                }
            }
        }
        // We always return ok. You don't want to do that in production ;)
        return Results.ok();

    }
}
