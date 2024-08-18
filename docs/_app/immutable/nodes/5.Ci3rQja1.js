import{s as $,n as P,o as ee,h as te,a as ne,i as X,u as le,g as se,b as ie,r as Z,j as K}from"../chunks/scheduler.Be45oRHX.js";import{S as O,i as W,e as w,c as b,a as T,d as v,y as D,g as E,s as M,f as R,q as A,r as o,h as C,v as I,n as y,o as B,t as ae,b as re,j as oe,k as G,l as U,m as H,p as S}from"../chunks/index.BkZwV-50.js";import{N as de}from"../chunks/NavigationBar.BwGKJxSd.js";import{C as fe}from"../chunks/ContentPage.CVTH3zt9.js";function ue(l){let e;return{c(){e=w("div"),this.h()},l(n){e=b(n,"DIV",{style:!0}),T(e).forEach(v),this.h()},h(){D(e,"width",l[0]),D(e,"height",l[1])},m(n,s){E(n,e,s),l[5](e)},p(n,[s]){s&1&&D(e,"width",n[0]),s&2&&D(e,"height",n[1])},i:P,o:P,d(n){n&&v(e),l[5](null)}}}function ce(l,e,n){let{javaCode:s=""}=e,{width:a="800px"}=e,{height:r="400px"}=e,{revealLines:c=[]}=e,d,g;ee(()=>{const t=document.createElement("script");return t.src="https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.21.2/min/vs/loader.js",t.onload=_,document.body.appendChild(t),()=>{d&&d.dispose()}});function _(){window.require.config({paths:{vs:"https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.21.2/min/vs"}}),window.require(["vs/editor/editor.main"],function(){d=monaco.editor.create(g,{value:s,language:"java",minimap:{enabled:!0},folding:!0,automaticLayout:!0,readOnly:!0,theme:"vs-dark",foldingImportsByDefault:!0});const t={provideFoldingRanges(u,f,m){let x=[],L=-1,V=-1;for(let k=1;k<=u.getLineCount();k++){let j=u.getLineContent(k);if(j.trim().startsWith("import ")?(L===-1&&(L=k),V=k):L!==-1&&(x.push({start:L,end:V,kind:monaco.languages.FoldingRangeKind.Imports}),L=-1,V=-1),j.trim().endsWith("{")){let q=k,F=i(u,k);F>q&&x.push({start:q,end:F,kind:monaco.languages.FoldingRangeKind.Region})}}return L!==-1&&x.push({start:L,end:V,kind:monaco.languages.FoldingRangeKind.Imports}),x}};function i(u,f){let m=1;for(let x=f+1;x<=u.getLineCount();x++){let L=u.getLineContent(x);if(m+=(L.match(/{/g)||[]).length,m-=(L.match(/}/g)||[]).length,m===0)return x}return f}monaco.languages.registerFoldingRangeProvider("java",t);function h(u){u.forEach(f=>{if(Object.prototype.toString.call(f)==="[object Array]"){setTimeout(function(){h(f)},200);return}d.setSelection({startLineNumber:f,startColumn:1,endLineNumber:f,endColumn:1}),d.trigger("keyboard","editor.unfold")})}setTimeout(function(){d.trigger("keyboard","editor.foldAll"),h(c)},200)})}function p(t){te[t?"unshift":"push"](()=>{g=t,n(2,g)})}return l.$$set=t=>{"javaCode"in t&&n(3,s=t.javaCode),"width"in t&&n(0,a=t.width),"height"in t&&n(1,r=t.height),"revealLines"in t&&n(4,c=t.revealLines)},[a,r,g,s,c,p]}class ge extends O{constructor(e){super(),W(this,e,ce,ue,$,{javaCode:3,width:0,height:1,revealLines:4})}}function Y(l){let e,n,s;return{c(){e=w("div"),n=w("a"),s=ae(l[4]),this.h()},l(a){e=b(a,"DIV",{class:!0});var r=T(e);n=b(r,"A",{href:!0,target:!0,class:!0});var c=T(n);s=re(c,l[4]),c.forEach(v),r.forEach(v),this.h()},h(){o(n,"href",l[3]),o(n,"target","_blank"),o(n,"class","svelte-70g0gi"),o(e,"class","link svelte-70g0gi")},m(a,r){E(a,e,r),C(e,n),C(n,s)},p(a,r){r&16&&oe(s,a[4]),r&8&&o(n,"href",a[3])},d(a){a&&v(e)}}}function he(l){let e,n,s,a,r="CLOSE",c,d,g,_,p;const t=l[8].default,i=ne(t,l,l[7],null);let h=l[3]&&Y(l);return{c(){e=w("div"),n=w("div"),i&&i.c(),s=M(),a=w("div"),a.textContent=r,c=M(),h&&h.c(),this.h()},l(u){e=b(u,"DIV",{class:!0});var f=T(e);n=b(f,"DIV",{class:!0,style:!0,role:!0,tabindex:!0});var m=T(n);i&&i.l(m),s=R(m),a=b(m,"DIV",{class:!0,role:!0,tabindex:!0,"data-svelte-h":!0}),A(a)!=="svelte-1pcaqjw"&&(a.textContent=r),c=R(m),h&&h.l(m),m.forEach(v),f.forEach(v),this.h()},h(){o(a,"class","close svelte-70g0gi"),o(a,"role","button"),o(a,"tabindex","-1"),o(n,"class","img-box svelte-70g0gi"),D(n,"--full-width",l[1]+"px"),D(n,"--full-height",l[2]+"px"),o(n,"role","button"),o(n,"tabindex","-1"),o(e,"class",d="parent "+(l[0]?"expanded":"")+" svelte-70g0gi")},m(u,f){E(u,e,f),C(e,n),i&&i.m(n,null),C(n,s),C(n,a),C(n,c),h&&h.m(n,null),g=!0,_||(p=[I(a,"click",l[6]),I(a,"keydown",l[6]),I(n,"click",function(){X(l[5])&&l[5].apply(this,arguments)}),I(n,"keydown",function(){X(l[5])&&l[5].apply(this,arguments)})],_=!0)},p(u,[f]){l=u,i&&i.p&&(!g||f&128)&&le(i,t,l,l[7],g?ie(t,l[7],f,null):se(l[7]),null),l[3]?h?h.p(l,f):(h=Y(l),h.c(),h.m(n,null)):h&&(h.d(1),h=null),(!g||f&2)&&D(n,"--full-width",l[1]+"px"),(!g||f&4)&&D(n,"--full-height",l[2]+"px"),(!g||f&1&&d!==(d="parent "+(l[0]?"expanded":"")+" svelte-70g0gi"))&&o(e,"class",d)},i(u){g||(y(i,u),g=!0)},o(u){B(i,u),g=!1},d(u){u&&v(e),i&&i.d(u),h&&h.d(),_=!1,Z(p)}}}function me(l,e,n){let{$$slots:s={},$$scope:a}=e,{expanded:r=!1}=e,{width:c}=e,{height:d}=e,{linkUrl:g=null}=e,{linkText:_="LINK"}=e,{onClick:p=null}=e;function t(){n(0,r=!1)}return l.$$set=i=>{"expanded"in i&&n(0,r=i.expanded),"width"in i&&n(1,c=i.width),"height"in i&&n(2,d=i.height),"linkUrl"in i&&n(3,g=i.linkUrl),"linkText"in i&&n(4,_=i.linkText),"onClick"in i&&n(5,p=i.onClick),"$$scope"in i&&n(7,a=i.$$scope)},[r,c,d,g,_,p,t,a,s]}class N extends O{constructor(e){super(),W(this,e,me,he,$,{expanded:0,width:1,height:2,linkUrl:3,linkText:4,onClick:5})}}function ve(l){let e,n;return{c(){e=w("img"),this.h()},l(s){e=b(s,"IMG",{class:!0,src:!0,alt:!0,width:!0,height:!0}),this.h()},h(){o(e,"class","result-img"),K(e.src,n="i9-Results.png")||o(e,"src",n),o(e,"alt","i9 Results"),o(e,"width","634px"),o(e,"height","371px")},m(s,a){E(s,e,a)},p:P,d(s){s&&v(e)}}}function _e(l){let e,n;return{c(){e=w("img"),this.h()},l(s){e=b(s,"IMG",{class:!0,src:!0,alt:!0,width:!0,height:!0}),this.h()},h(){o(e,"class","result-img"),K(e.src,n="m6-Results.png")||o(e,"src",n),o(e,"alt","m6 Results"),o(e,"width","634px"),o(e,"height","371px")},m(s,a){E(s,e,a)},p:P,d(s){s&&v(e)}}}function pe(l){let e,n;return e=new ge({props:{javaCode:l[0].text,revealLines:[[39,[371,372,[375,[380,[1]]]]]],width:"1000px",height:"600px"}}),{c(){G(e.$$.fragment)},l(s){U(e.$$.fragment,s)},m(s,a){H(e,s,a),n=!0},p(s,a){const r={};a&1&&(r.javaCode=s[0].text),e.$set(r)},i(s){n||(y(e.$$.fragment,s),n=!0)},o(s){B(e.$$.fragment,s),n=!1},d(s){S(e,s)}}}function we(l){let e,n,s,a,r,c,d,g='<div class="svelte-w70fdd">i9 13Gen (32 vCPU) - 128GB</div> <div class="svelte-w70fdd"><b class="svelte-w70fdd">2.1507</b> seconds</div>',_,p,t,i='<div class="svelte-w70fdd">m6a.8xlarge (32 vCPU) - 128GB</div> <div class="svelte-w70fdd"><b class="svelte-w70fdd">3.215</b> seconds</div>',h,u,f,m,x='<div class="svelte-w70fdd">No &quot;Unsafe&quot; - No GraalVM</div> <div class="svelte-w70fdd">Readable Code!</div>',L,V;return{c(){e=w("img"),s=M(),a=w("table"),r=w("tr"),c=w("td"),d=w("div"),d.innerHTML=g,_=M(),p=w("td"),t=w("div"),t.innerHTML=i,h=M(),u=w("tr"),f=w("td"),m=w("div"),m.innerHTML=x,this.h()},l(k){e=b(k,"IMG",{class:!0,src:!0,alt:!0}),s=R(k),a=b(k,"TABLE",{class:!0});var j=T(a);r=b(j,"TR",{});var q=T(r);c=b(q,"TD",{class:!0});var F=T(c);d=b(F,"DIV",{class:!0,role:!0,tabindex:!0,"data-svelte-h":!0}),A(d)!=="svelte-v3yzwh"&&(d.innerHTML=g),F.forEach(v),_=R(q),p=b(q,"TD",{class:!0});var z=T(p);t=b(z,"DIV",{class:!0,role:!0,tabindex:!0,"data-svelte-h":!0}),A(t)!=="svelte-476xny"&&(t.innerHTML=i),z.forEach(v),q.forEach(v),h=R(j),u=b(j,"TR",{});var J=T(u);f=b(J,"TD",{colspan:!0,class:!0});var Q=T(f);m=b(Q,"DIV",{class:!0,role:!0,tabindex:!0,"data-svelte-h":!0}),A(m)!=="svelte-1kubnrf"&&(m.innerHTML=x),Q.forEach(v),J.forEach(v),j.forEach(v),this.h()},h(){o(e,"class","figure svelte-w70fdd"),K(e.src,n="smug-daniel-craig.gif")||o(e,"src",n),o(e,"alt","So pround"),o(d,"class","result r-i9 svelte-w70fdd"),o(d,"role","button"),o(d,"tabindex","-1"),o(c,"class","left svelte-w70fdd"),o(t,"class","result r-m6a svelte-w70fdd"),o(t,"role","button"),o(t,"tabindex","-1"),o(p,"class","right svelte-w70fdd"),o(m,"class","code svelte-w70fdd"),o(m,"role","button"),o(m,"tabindex","-1"),o(f,"colspan","2"),o(f,"class","svelte-w70fdd"),o(a,"class","text svelte-w70fdd")},m(k,j){E(k,e,j),E(k,s,j),E(k,a,j),C(a,r),C(r,c),C(c,d),C(r,_),C(r,p),C(p,t),C(a,h),C(a,u),C(u,f),C(f,m),L||(V=[I(d,"click",l[4]),I(d,"keydown",l[4]),I(t,"click",l[5]),I(t,"keydown",l[5]),I(m,"click",l[6]),I(m,"keydown",l[6])],L=!0)},p:P,d(k){k&&(v(e),v(s),v(a)),L=!1,Z(V)}}}function be(l){let e,n,s,a,r,c,d,g,_,p;return e=new N({props:{expanded:l[1],width:634,height:371,onClick:l[4],$$slots:{default:[ve]},$$scope:{ctx:l}}}),s=new N({props:{expanded:l[2],width:634,height:371,onClick:l[5],$$slots:{default:[_e]},$$scope:{ctx:l}}}),r=new N({props:{expanded:l[3],width:1e3,height:600,linkText:"View on GitHub",linkUrl:"https://github.com/NawaMan/OneBRC/blob/main/src/onebrc/CalculateAverage_nawaman.java",$$slots:{default:[pe]},$$scope:{ctx:l}}}),d=new fe({props:{title:"Results",$$slots:{default:[we]},$$scope:{ctx:l}}}),_=new de({props:{prevLink:"/overview.html",nextLink:"/challenge.html"}}),{c(){G(e.$$.fragment),n=M(),G(s.$$.fragment),a=M(),G(r.$$.fragment),c=M(),G(d.$$.fragment),g=M(),G(_.$$.fragment)},l(t){U(e.$$.fragment,t),n=R(t),U(s.$$.fragment,t),a=R(t),U(r.$$.fragment,t),c=R(t),U(d.$$.fragment,t),g=R(t),U(_.$$.fragment,t)},m(t,i){H(e,t,i),E(t,n,i),H(s,t,i),E(t,a,i),H(r,t,i),E(t,c,i),H(d,t,i),E(t,g,i),H(_,t,i),p=!0},p(t,[i]){const h={};i&2&&(h.expanded=t[1]),i&128&&(h.$$scope={dirty:i,ctx:t}),e.$set(h);const u={};i&4&&(u.expanded=t[2]),i&128&&(u.$$scope={dirty:i,ctx:t}),s.$set(u);const f={};i&8&&(f.expanded=t[3]),i&129&&(f.$$scope={dirty:i,ctx:t}),r.$set(f);const m={};i&128&&(m.$$scope={dirty:i,ctx:t}),d.$set(m)},i(t){p||(y(e.$$.fragment,t),y(s.$$.fragment,t),y(r.$$.fragment,t),y(d.$$.fragment,t),y(_.$$.fragment,t),p=!0)},o(t){B(e.$$.fragment,t),B(s.$$.fragment,t),B(r.$$.fragment,t),B(d.$$.fragment,t),B(_.$$.fragment,t),p=!1},d(t){t&&(v(n),v(a),v(c),v(g)),S(e,t),S(s,t),S(r,t),S(d,t),S(_,t)}}}function ke(l,e,n){let{data:s}=e,a=!1,r=!1,c=!1;function d(){n(1,a=!a)}function g(){n(2,r=!r)}function _(){n(3,c=!c)}return l.$$set=p=>{"data"in p&&n(0,s=p.data)},[s,a,r,c,d,g,_]}class je extends O{constructor(e){super(),W(this,e,ke,be,$,{data:0})}}export{je as component};
