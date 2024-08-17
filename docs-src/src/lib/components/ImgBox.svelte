<script lang="ts">
    export let expanded = false;    // Control expansion
    export let imgSrc;              // Image source URL
    export let altText;             // Alt text for the image
    export let width;               // Image width
    export let height;              // Image height
	export let onClick: (() => void) | null = null;
</script>
  
<div class="parent {expanded ? 'expanded' : ''}">
    <div class="img-box" style="--full-width: {width}px; --full-height: {height}px;">
        <img src={imgSrc} alt={altText} width={width} height={height} on:click={onClick} />
    </div>
</div>
  
  <style>
    .parent {
        --box-ani-time: 0.4s;
        position: absolute;
        top: 50%;
        left: 50%;
        width: 0px;
        height: 0px;
        display: block;
        transition:
            var(--box-ani-time) top,
            var(--box-ani-time) left;
    }
    .parent.expanded {
        top: 50%;
        left: 50%;
        transition:
            var(--box-ani-time) top,
            var(--box-ani-time) left;
    }
  
    .parent .img-box {
        padding: auto;
        border: none;
        overflow: hidden;
        width: 0px;
        height: 0px;
        margin-top: 50%;
        margin-left: 50%;
        box-shadow: none;
        transition:
            var(--box-ani-time) height,
            var(--box-ani-time) width,
            var(--box-ani-time) margin,
            var(--box-ani-time) box-shadow,
            var(--box-ani-time) border,
            var(--box-ani-time) padding;
    }
  
    .parent.expanded .img-box {
        padding: 0px;
        border-radius: 3px;
        border: 5px solid lightblue;
        width: var(--full-width);
        height: var(--full-height);
        margin-top: calc(50% - var(--full-height) / 2);
        margin-left: calc(50% - var(--full-width) / 2);
        box-shadow: 2px 2px 10px black, 0 0 0 9999px rgb(0, 0, 0, 0.5);
        transition:
            var(--box-ani-time) height,
            var(--box-ani-time) width,
            var(--box-ani-time) margin,
            var(--box-ani-time) box-shadow,
            var(--box-ani-time) border,
            var(--box-ani-time) padding;
    }
  </style>
  