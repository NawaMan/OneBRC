// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "Due the size of the files, read speed is one of the biggest factor. "
            + "Also, different reading methods end up with data in different format which has different performance chracteristic. "
            + "Before we dive into the alternatives, we need to keep in mind that the foundamental data structure is always array of bytes. "
            + "A string is backed by an array of bytes and since bytes are multable every new string will has itsown array of bytes. ";
    return { noteContent };
};
