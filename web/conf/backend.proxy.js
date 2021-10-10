const backendServerUrl = process.env.tsWebBackendUrl || "http://localhost:2041";
const backendWebAppPath = "/" + (process.env.tsWebBackendContext || "");

console.log("");
console.log("Backend server app is configured as: ");
console.log("       " + backendServerUrl + backendWebAppPath);
console.log("This is for the dev environment to connect to a ");
console.log("  backend web app hosted at a different URL.");
console.log("");
console.log(
  "To change this, please define or modify the following two environment variables:"
);
console.log(`   tsWebBackendUrl=${process.env.tsWebBackendUrl || ""}`);
console.log(`   tsWebBackendContext=${process.env.tsWebBackendContext || ""}`);
console.log("");

module.exports = {
  "/": {
    target: backendServerUrl,
    pathRewrite: {
      "^/": backendWebAppPath + "/",
    },
    cookieDomainRewrite: "*",
    changeOrigin: true,
    autoRewrite: true,
    onProxyRes: (proxyRes, req, res) => {
      proxyRes.headers["set-cookie"] =
        [
          (proxyRes.headers["set-cookie"] || "")
            .toString()
            .replace("path=" + backendWebAppPath, "path=/"),
        ] || undefined;
      proxyRes.headers["location"] =
        (proxyRes.headers["location"] || "")
          .toString()
          .replace(backendWebAppPath, "") || undefined;
    },
  },
};
