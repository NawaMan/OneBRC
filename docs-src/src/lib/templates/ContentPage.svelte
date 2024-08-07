<script lang="ts">
    export let title = "";
    export let subtitle = "";
</script>

<style>
.page {
    /* cosmetic */
    width: 100%;
    height: 100%;
}
.page .subtitle {
    /* cosmetic */
    display: block;
    margin: 1em 0;
}

.page .subtitle::after {
    content: '';
    display: block;
    margin-top: 10px; /* Adjust space after <br> */
    border-bottom: 1px solid black; /* Add a line after <br> */
}
</style>

<div class="page">
    <h1>{title}</h1>
    <span class="subtitle">{subtitle}</span>
    <div class="content">
        <slot />
    </div>
</div>