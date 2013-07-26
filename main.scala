import java.io._


 object slist {
 	var list_parameter_ = false
 	var all_parameter_ = false
 	var path_ = new File(".").getCanonicalPath

    def main(args: Array[String]) {
      list(path_)
    }

    def list(path: String) {
    	try {
    		var currentDirectory = new File(path)
    		for (f <- currentDirectory.listFiles()){
    			if (f.isDirectory()) println("\td\t" + f)
    			else if (f.isFile()) println("\tf\t" + f)
    		} 
    	}
    }
  }