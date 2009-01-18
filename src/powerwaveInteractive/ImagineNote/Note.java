package powerwaveInteractive.ImagineNote;


import android.net.Uri;
import android.provider.BaseColumns;

public final class Note implements BaseColumns{
	// This class cannot be instantiated
    private Note() {}
    
    
    public static final String AUTHORITY = "powerwaveInteractive.ImagineNote";

    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/Note");

    /**
     * The MIME type of {@link #CONTENT_URI} providing a directory of Note.
     */
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.note";

    /**
     * The MIME type of a {@link #CONTENT_URI} sub-directory of a single note.
     */
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.note";

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "modified DESC";

    /**
     * The title of the note
     * <P>Type: TEXT</P>
     */
    public static final String TITLE = "title";

    /**
     * The note itself
     * <P>Type: TEXT</P>
     */
    public static final String NOTE = "note";

    /**
     * The timestamp for when the note was created
     * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
     */
    public static final String CREATED_DATE = "created";

    /**
     * The timestamp for when the note was last modified
     * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
     */
    public static final String MODIFIED_DATE = "modified";

}
