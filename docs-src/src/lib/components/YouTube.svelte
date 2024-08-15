<script>
    export let name;
    export let youtubeId;

    $: thumbnailSrc = `${name}.png`;
    $: thumbnailAlt = name;
    $: qrSrc        = `${name}-QR.png`;
    $: qrAlt        = `${name}-qr`;
    $: videoUrl     = `https://www.youtube.com/watch?v=${youtubeId}`;
</script>

<a href={videoUrl}>
    <div class="youtube">
        <img class="thumbnail" src={thumbnailSrc} alt={thumbnailAlt} />
        <img class="qr" src={qrSrc} alt={qrAlt} />
    </div>
</a>

<style>
img {
    display: block;
    margin-left: auto;
    margin-right: auto;
    margin-bottom: 1em;
}
.youtube {
    position: relative;
    margin: auto;
    width: 600px;
}
.thumbnail {
    width: 100%;
}
.qr {
    position: absolute;
    width: 100px;
    bottom: -15px;
    left: 10px;
}
</style>