// This can be false if you're using a fallback (i.e. SPA mode)
export const prerender = true;
export const trailingSlash = "never";

export const load = () => {
    const noteContent
            = "Due the size of the files, read speef is one of the biggest factor."
            + "Also, different reading methods end up with data in different format which has different performance chracteristic."
            + "Here are some choices: ..."
            + "But before disucssion the choices, we need to keep in mind that the founable datastructure is array of bytes."
            + "A string has array of bytes in the back and since bytes are multable every new string will has itsown array of bytes.";
    return { noteContent };
};
