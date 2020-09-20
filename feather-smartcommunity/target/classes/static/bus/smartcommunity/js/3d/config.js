featherCmsScript.register({
    element : "",
    onLoad : function(cmsOptions) {
        var config = {
            cfg:{
                SXT:{id:'SXTID',img:'bus/smartcommunity/img/camera.png',url:'bus/smartcommunity/json/SXT.json'},
                MJ:{id:'MJID',img:'bus/smartcommunity/img/door.png',url:'bus/smartcommunity/json/MJ.json'},
                ZJ:{id:'ZJID',img:'bus/smartcommunity/img/zj.png',url:'bus/smartcommunity/json/ZJ.json'},
                DG:{id:'DGID',img:'bus/smartcommunity/img/dg.png',url:'bus/smartcommunity/json/DG.json'},
                YC:{id:'YCID',img:'bus/smartcommunity/img/warn.png',url:'bus/smartcommunity/json/YC.json'},
                LJT:{id:'LJTID',img:'bus/smartcommunity/img/ljt.png',url:'bus/smartcommunity/json/LJT.json'},
                YG:{id:'YGID',img:'bus/smartcommunity/img/smoke.png',url:'bus/smartcommunity/json/YG.json'},
                CDZ:{id:'CDZID',img:'bus/smartcommunity/img/cdz.png',url:'bus/smartcommunity/json/CDZ.json'},
                LD:{id:'LDID',img:'bus/smartcommunity/img/light.png',url:'bus/smartcommunity/json/ZNLD.json'},
                XFS:{id:'XFSID',img:'bus/smartcommunity/img/xfs.png',url:'bus/smartcommunity/json/XFS.json'},
                FW:{id:'LDID',img:'bus/smartcommunity/img/sq.png',url:'bus/smartcommunity/json/LD_D.json'},
                RY:{id:'LDID',img:'bus/smartcommunity/img/xq.png',url:'bus/smartcommunity/json/LD_D.json'},
                TCC:{id:'TCCID',img:'bus/smartcommunity/img/p.png',url:'bus/smartcommunity/json/TCC.json'},
            }
        }

        featherCmsScript._cfg = config;
    }
});


