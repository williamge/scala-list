import java.io._
import java.nio._
import java.nio.file._
import java.util.Date
import scala.collection.mutable.ListBuffer

 object slist {
 	//flags to be set from command line arguments
 	var long_format_param_ = false //long form list formatting
 	var all_param_ = false //display all, even hidden
    var recurse_param_ = false //resursively list directories
    var sort_param_ = true //sort files list before display
    var sort_mod_param_ = false //flag for sorting by modification time
    var reverse_sort_param_ = false //reverses each sorting
    var sort_size_param_ = false //sort list by size

 	var path_ = new File(".").getCanonicalPath

 	/** Handles user interaction such as parameters. */
    def main(args: Array[String]) {
    	for (argument <- args){
            if (argument startsWith("--")) argument match {
                case "--long" => long_format_param_ = true
                case "--all" => all_param_ = true
            } else if (argument.head == '-') for (letter_option <- argument.tail) letter_option match {
    			case 'l'  => long_format_param_ = true
    			case 'a' => all_param_ = true
                case 'R' => recurse_param_ = true
                case 'f' => sort_param_ = false
                case 't' => sort_mod_param_ = true
                case 'r' => reverse_sort_param_ = true
                case 'S' => sort_size_param_ = true
    		}
    		/* if it doesn't start with a '-' character then it otherwise must be the path, so we see if it's 
    		 * at the end of the arguments, in the correct spot */
    		else if ((args indexOf argument) == args.length - 1) path_ = argument
    	}
      list(path_)
    }

    /** Prints a given file according to formatting parameters.
      * @param item item to be listed 
      * @param depth depth in the directory tree for item
      */
    def printFileItem(item: Path, depth: Int = 0)
    {

        def containsPermission(permissions: Set[attribute.PosixFilePermission], value: String): Boolean = {
            permissions.contains(attribute.PosixFilePermission.valueOf(value))
        }

    	if (!Files.isHidden(item) || all_param_) {
    		long_format_param_ match {
    			case false => print(item.getFileName.toString + " ")
    			case true => {
                    val permissions = Files.getPosixFilePermissions(item)
                    def containsPermission(value: String): Boolean = { //just a helper function to make it more readable
                        permissions.contains(attribute.PosixFilePermission.valueOf(value))
                    }

                    println( {if (Files.isDirectory(item)) "d" else "-"} + 
                        {if (containsPermission("OWNER_READ")) "r" else "-" } + 
                        {if (containsPermission("OWNER_WRITE")) "w" else "-" } + 
                        {if (containsPermission("OWNER_EXECUTE")) "x" else "-" } + 
                        {if (containsPermission("GROUP_READ") )"r" else "-" } + 
                        {if (containsPermission("GROUP_WRITE") )"w" else "-" } + 
                        {if (containsPermission("GROUP_EXECUTE")) "x" else "-" } + 
                        {if (containsPermission("OTHERS_READ") )"r" else "-" } + 
                        {if (containsPermission("OTHERS_WRITE") )"w" else "-" } + 
                        {if (containsPermission("OTHERS_EXECUTE")) "x" else "-" } + 
        				"\t" + Files.size(item) + 
                        "\t" + (new Date(Files.getLastModifiedTime(item).toMillis)).toString() + 
                        " " + "\t" * depth + item.getFileName.toString)
                }
    		}
	    }		
    }

    /** Handles the listing logic for each query.
      * @param path path to be listed
      * @param depth depth in the directory tree for path
   	  */
    def list(path: String, depth: Int = 0) {
    	try {
    		var currentDirectory = Files.newDirectoryStream(Paths.get("."))
            val dirIt = currentDirectory.iterator

            var filesList : ListBuffer[Path] = ListBuffer()
            while (dirIt hasNext){
                filesList += dirIt.next()
            }

            //TODO: find out if Java lists files alphabetically anyway
            if (sort_param_ && !(sort_mod_param_ || sort_size_param_)) {
                filesList = filesList.sortWith(
                    _.getFileName.toString.toLowerCase < _.getFileName.toString.toLowerCase)
            } else if (sort_mod_param_) {
                filesList = filesList.sortWith(
                    Files.getLastModifiedTime(_).toMillis < Files.getLastModifiedTime(_).toMillis)
            } else if (sort_size_param_) {
                filesList = filesList.sortWith(
                    Files.size(_) < Files.size(_))
            }

            if (reverse_sort_param_) filesList = filesList.reverse

    		for (p <- filesList) {
                printFileItem(p, depth)
                if (recurse_param_ && Files.isDirectory(p)) list(p.toString, depth + 1)
            }
    		if (!long_format_param_) println() //print a newline for formatting when each file doesn't get it's own line
    	} catch {
    		case _: NullPointerException => println("\"" + path_ + "\" is an invalid path")
    	}
    }
  }