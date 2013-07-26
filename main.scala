import java.io._

 object slist {
 	//flags to be set from command line arguments
 	var long_format_param_ = false
 	var all_param_ = false
 	var path_ = new File(".").getCanonicalPath

 	/** Handles user interaction such as parameters. */
    def main(args: Array[String]) {
    	for (argument <- args){
            if (argument startsWith("--")) argument match {
                case "--list" => long_format_param_ = true
                case "--all" => all_param_ = true
            } else if (argument.head == '-') for (letter_option <- argument.tail) letter_option match {
    			case 'l'  => long_format_param_ = true
    			case 'a' => all_param_ = true
    		}
    		/* if it doesn't start with a '-' character then it otherwise must be the path, so we see if it's 
    		 * at the end of the arguments, in the correct spot */
    		else if ((args indexOf argument) == args.length - 1) path_ = argument
    	}
      list(path_)
    }

    /** Prints a given file according to formatting parameters.
      * @param item item to be listed 
      */
    def printFileItem(item: File)
    {
    	if (!item.isHidden || all_param_) {
	    	if (item.isDirectory) {
	    		long_format_param_ match {
	    			case false => print(item.getName + " ")
	    			case true => println("d" + 
	    				{if (item.canRead) "r" else "-"} + 
	    				{if (item.canWrite) "w" else "-"} + 
	    				{if (item.canExecute) "x" else "-"} +
	    				"\t" + item.getName)
	    		}
	    	} else if (item.isFile) {
	    		long_format_param_ match {
	    			case false => print(item.getName + " ")
	    			case true => println("-" + 
	    				{if (item.canRead) "r" else "-"} + 
	    				{if (item.canWrite) "w" else "-"} + 
	    				{if (item.canExecute) "x" else "-"} +
	    				"\t" + item.getName)
	    		}
	    	}
	    }
    		
    }
    /** Handles the listing logic for each query.
      * @param path path to be listed
   	  */
    def list(path: String) {
    	try {
    		var currentDirectory = new File(path)

    		for (f <- currentDirectory.listFiles()) printFileItem(f)
    		if (!long_format_param_) println() //print a newline for formatting when each file doesn't get it's own line
    	} catch {
    		case _: NullPointerException => println("\"" + path_ + "\" is an invalid path")
    	}
    }
  }