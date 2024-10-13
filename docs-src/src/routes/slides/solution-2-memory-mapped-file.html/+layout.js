// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "<p>"
                + "The next trick is due the large size of the file, the read speed is one of the one biggest factor of the overall performce. "
                + "Different reading methods not only have different performance characteristics, "
                + "it also returns different data format with different performance chracteristic of itsown. "
                + "Another word, we need to consider both reading and processing speed. of each alternative."
            + "</p>"
            + "<p>"
                + "Before diving into the alternatives, we need to keep in mind that the foundamental data structure is always array of bytes. "
                + "A string is backed by an array of bytes and since bytes are multable every new string will has itsown array of bytes. "
            + "</p>"
            + "<ol>"
            + "<li>"
                + "With that in mind, the first alternative is read the whole file or the whole chunk into a string "
                + "but that means double memory and copy time to create array of bytes for each station name as a string."
            +"</li> "
            + "<li>Next alternative is to do it line by line but that suffer similar problem.</li> "
            + "<li>Next, we can use RandomAccessFile --- but its position index is in <code>int</code> which is not big enough for 13GB file.</li> "
            + "<li>The next alternative is to use memory mapped files which can be done in two ways: "
            + "<ol>"
            + "<li>direct mapping native memory outside of JVM,  and</li>"
            + "<li> mapping to heap.</li> "
            + "</ol>"
            + "Direct mapping to native memory is faster to read but the result data structure is slow to process -- in the way we need to, anyway. <br/>"
            + "So the fastest choice ended be mapping file to heap. <br/>"
            + "</li>"
            + "</ol>";
    return { noteContent };
};
